package com.stringconcat.ddd.e2e.steps

import com.stringconcat.ddd.e2e.MealId
import com.stringconcat.ddd.e2e.mealDescription
import com.stringconcat.ddd.e2e.mealName
import com.stringconcat.ddd.e2e.menuUrl
import com.stringconcat.ddd.e2e.price
import io.qameta.allure.Step
import org.apache.http.HttpHeaders
import org.koin.core.KoinComponent
import ru.fix.kbdd.asserts.asString
import ru.fix.kbdd.asserts.get
import ru.fix.kbdd.asserts.isEquals
import ru.fix.kbdd.asserts.isMatches
import ru.fix.kbdd.rest.Rest
import ru.fix.kbdd.rest.Rest.headers
import ru.fix.kbdd.rest.Rest.json
import ru.fix.kbdd.rest.Rest.statusCode

class MenuSteps : KoinComponent {

    @Step
    suspend fun `Add a new meal`(): MealId {
        Rest.request {
            body(json {
                "name" % mealName()
                "description" % mealDescription()
                "price" % price()
            })
            post(menuUrl())
        }
        statusCode().isEquals(201)

        val location = headers()[HttpHeaders.LOCATION]
        location.isMatches("${menuUrl()}/\\d+")

        val id = Regex("\\d+\$").find(location.asString())?.value
        return MealId(id!!.toLong())
    }
}