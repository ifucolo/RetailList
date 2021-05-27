package com.retaillist.util

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.jetbrains.spek.api.dsl.SpecBody
import org.junit.runner.Description

fun SpecBody.instantTaskExecutorRule() {
    val archRule = object : InstantTaskExecutorRule() {
        public override fun starting(description: Description?) {
            super.starting(description)
        }

        public override fun finished(description: Description?) {
            super.finished(description)
        }
    }

    beforeEachTest { archRule.starting(null) }
    afterEachTest { archRule.finished(null) }
}