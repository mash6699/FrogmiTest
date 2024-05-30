package com.mash.frogmi.domain.model.ex

import java.io.IOException

class ApiException(val code: Int, message: String): IOException(message)
