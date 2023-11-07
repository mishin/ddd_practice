package com.stringconcat.ddd.e2e.cases

import com.stringconcat.ddd.e2e.E2eTestTelnetClient
import com.stringconcat.ddd.e2e.CONFIRM
import com.stringconcat.ddd.e2e.KITCHEN
import com.stringconcat.ddd.e2e.KITCHEN_URL
import com.stringconcat.ddd.e2e.MENU
import com.stringconcat.ddd.e2e.MENU_URL
import com.stringconcat.ddd.e2e.MealId
import com.stringconcat.ddd.e2e.ORDERS
import com.stringconcat.ddd.e2e.ORDERS_URL
import com.stringconcat.ddd.e2e.OrderId
import com.stringconcat.ddd.e2e.Url
import com.stringconcat.ddd.e2e.steps.CartSteps
import com.stringconcat.ddd.e2e.steps.CrmSteps
import com.stringconcat.ddd.e2e.steps.MenuSteps
import com.stringconcat.ddd.e2e.steps.OrderSteps
import com.stringconcat.ddd.e2e.steps.UrlSteps
import com.stringconcat.ddd.tests.common.StandConfiguration
import io.qameta.allure.Epic
import io.qameta.allure.Story
import org.junit.jupiter.api.Test
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.fix.corounit.allure.invoke
import ru.fix.corounit.allure.repeatUntilSuccess

@Epic("Deliver an order")
class OrderAndCookCase : KoinComponent {

    val Url by inject<UrlSteps>()
    val Menu by inject<MenuSteps>()
    val Cart by inject<CartSteps>()
    val Order by inject<OrderSteps>()
    val Settings by inject<StandConfiguration>()

    val Crm by inject<CrmSteps>()

    @Test
    @Story("Cook an order")
    suspend fun `cook an order test`() {

        val urls = mapOf(MENU to Url(MENU_URL), ORDERS to Url(ORDERS_URL), KITCHEN to Url(KITCHEN_URL))
        val telnet = E2eTestTelnetClient("localhost", Settings.shopTelnetPort)

        var mealId = MealId(0)
        "Prepare menu" {
            mealId = Menu.`Add a new meal`(urls[MENU]!!)
        }

        var orderInfo = Pair(OrderId(0), Url(""))
        "Build and confirm order" {
            Cart.`Add meal to cart`(telnet, mealId)
            orderInfo = Cart.`Create an order`(telnet)
            Order.`Pay for the order`(orderInfo.second)
            val confirmUrl = Url(ORDERS_URL.plus("/").plus(orderInfo.first.value).plus(CONFIRM))
            Order.`Confirm order`(confirmUrl)
        }

        "Cook an order" {
            repeatUntilSuccess {
                val orderKitchenByIdUrl = Url.`Get kitchen order by id link`(urls[KITCHEN]!!, orderInfo.first)
                val cookUrl = Url.`Get cook order link`(orderKitchenByIdUrl)
                Order.`Cook order`(cookUrl)
            }
        }

        "Check crm after" {
            Crm.`Check crm after`(orderInfo.first)
        }
    }
}