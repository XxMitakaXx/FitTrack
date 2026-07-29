package com.example.fittrack.app.presentation.util

import co.touchlab.kermit.Logger

class KermitAppLogger: AppLogger {
    private val logger = Logger

    override fun i(message: String, tag: String?, throwable: Throwable?) {
        logger.i(throwable) { message }
    }

    override fun e(message: String, tag: String?, throwable: Throwable?) {
        logger.e(throwable) { message }
    }

    override fun w(message: String, tag: String?, throwable: Throwable?) {
        logger.e(throwable) { message }
    }

    override fun d(message: String, tag: String?, throwable: Throwable?) {
        logger.e(throwable) { message }
    }

    override fun v(message: String, tag: String?, throwable: Throwable?) {
        logger.e(throwable) { message }
    }


}