package com.stringconcat.ddd.e2e.steps

import com.stringconcat.ddd.e2e.Url
import org.koin.core.component.KoinComponent
import ru.fix.corounit.allure.Step
import ru.fix.kbdd.asserts.isEquals
import ru.fix.kbdd.rest.Rest

open class OrderSteps : KoinComponent {

    @Step
    open suspend fun `Pay for the order`(url: Url) {
        Rest.request {
            post(url.value)
        }
        Rest.statusCode().isEquals(200)
    }

    @Step
    open suspend fun `Confirm order`(url: Url) {
        Rest.request {
            put(url.value)
        }
        Rest.statusCode().isEquals(204)
    }

    @Step
    open suspend fun `Cook order`(url: Url) {
        Rest.request {
            put(url.value)
        }
        Rest.statusCode().isEquals(204)
    }
}