package com.mvvm_rcv_kot

import android.app.Application
import com.mvvm_rcv_kot.model.UserService

/*Класс App наследует Application, что делает его точкой входа
в ваше приложение. Внутри этого класса создается экземпляр UserService,
делая его доступным на уровне приложения. То есть, вы сможете получить
доступ к этому экземпляру UserService из любой части вашего приложения,
что упрощает управление пользователями и их данными на глобальном уровне.

Суть в том, что UserService предоставляет методы и функциональность для
работы с данными пользователей, а App класс делает этот сервис доступным
на уровне всего приложения.*/

class App: Application() {
    //создать экземляр класса UserService в singleton
    val userService = UserService()
}