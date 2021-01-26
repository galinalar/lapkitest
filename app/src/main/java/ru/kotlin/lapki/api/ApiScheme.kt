package ru.kotlin.lapki.api

object ApiScheme {

    private const val SERVER_URL = "https://192.168.0.76/lapki/v1"

    const val GET_USERS_URL = "$SERVER_URL/?op=getusers"

    const val GET_USER_ACCAUNT = "$SERVER_URL/?op=users"

    const val RESTORE_PASSWORD = "$SERVER_URL/?op=getusersemail"

    const val DELETE_OBJECT = "$SERVER_URL/?op=deleteobject"

    const val CHANGE_PASSWORD = "$SERVER_URL/?op=changepass"

    const val GET_SHELTER_LIST = "$SERVER_URL/?op=getlist"

    const val ADD_PET = "$SERVER_URL/?op=addpet"

    const val GET_PET_ROLE = "$SERVER_URL/?op=getrolep"

    const val GET_PET_ACCAUNT = "$SERVER_URL/?op=getpet"

    const val CHANGE_PET = "$SERVER_URL/?op=chpet"

    const val ADD_SHELTER = "$SERVER_URL/?op=addshelter"

    const val GET_SHELTER_TYPE = "$SERVER_URL/?op=gettype"

    const val GET_SHELTER_ROLE = "$SERVER_URL/?op=getrole"

    const val GET_SHELTER = "$SERVER_URL/?op=getshelter"

    const val CHANGE_SHELTER = "$SERVER_URL/?op=chshelter"

    const val ADD_USER = "$SERVER_URL/?op=addusers"






}