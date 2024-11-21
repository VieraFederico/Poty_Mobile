package hci.mobile.poty.data

class DataSourceException(
    var code: Int,
    message: String,
) : Exception(message)