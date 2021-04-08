package com.belajar.amikomevent.network

class ApiEndPoint {
//192.168.100.83
//192.168.43.111

    //sesuaikan dengan ip pc
    companion object{
        private val BASE_URL = "http://192.168.100.78/amikom_event/"
        val CREATE = BASE_URL + "create.php"
        val READ = BASE_URL + "read.php"
        val DELETE = BASE_URL + "delete.php"
        val UPDATE = BASE_URL + "update.php"
        val LOGIN = BASE_URL + "login.php"
        val LOGIN_ADMIN = BASE_URL + "loginadmin.php"
    }
}