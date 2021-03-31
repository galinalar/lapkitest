package ru.kotlin.lapki.api

object ApiScheme {

    private const val SERVER_URL = "https://192.168.0.106/lapki/v1"

    const val GET_USERS_URL = "$SERVER_URL/?op=getusers"

    const val CREATE_SESSION_URL = "$SERVER_URL/?op=createsession"

    const val GET_SESSION_URL = "$SERVER_URL/?op=getsession"

    const val GET_USER_URL = "$SERVER_URL/?op=user"

    const val RESTORE_PASSWORD_URL = "$SERVER_URL/?op=getusersemail"

    const val DELETE_OBJECT_URL = "$SERVER_URL/?op=deleteobject"

    const val CHANGE_PASSWORD_URL = "$SERVER_URL/?op=changepass"

    const val GET_LIST_URL = "$SERVER_URL/?op=getlist"

    const val ADD_PET_URL = "$SERVER_URL/?op=addpet"

    const val GET_PET_ROLE_URL = "$SERVER_URL/?op=getrolep"

    const val GET_USER_ROLE_URL = "$SERVER_URL/?op=getroleu"

    const val CHANGE_USER_URL = "$SERVER_URL/?op=chuser"

    const val GET_SHELTER_ADMIN_URL = "$SERVER_URL/?op=getshelteradmin"

    const val GET_PET_URL = "$SERVER_URL/?op=getpet"

    const val CHANGE_PET_URL = "$SERVER_URL/?op=chpet"

    const val ADD_SHELTER_URL = "$SERVER_URL/?op=addshelter"

    const val GET_SHELTER_TYPE_URL = "$SERVER_URL/?op=gettype"

    const val GET_SHELTER_ROLE_URL = "$SERVER_URL/?op=getrole"

    const val GET_SHELTER_URL = "$SERVER_URL/?op=getshelter"

    const val CHANGE_SHELTER_URL = "$SERVER_URL/?op=chshelter"

    const val ADD_USER_URL = "$SERVER_URL/?op=addusers"

    const val ADD_ADMIN_URL = "$SERVER_URL/?op=addshelteradmin"

    const val CHANGE_ADMIN_URL = "$SERVER_URL/?op=chshelteradmin"

    const val DELETE_ADMIN_URL = "$SERVER_URL/?op=delshelteradmin"

    const val REQUEST_VOL_URL = "$SERVER_URL/?op=getrequestvol"

    const val REQUEST_OWNER_URL = "$SERVER_URL/?op=getrequestowner"

    const val REQUEST_VOL_ANSWER_URL = "$SERVER_URL/?op=requestvolanswer"

    const val REQUEST_OWNER_ANSWER_URL = "$SERVER_URL/?op=requestowneranswer"

    const val ADD_REQUEST_VOL_URL = "$SERVER_URL/?op=addreqvol"

    const val CHANGE_REQUEST_VOL_URL = "$SERVER_URL/?op=chreqvol"

    const val GET_REQUEST_VOL_ROLE_URL = "$SERVER_URL/?op=getrolereq"

    const val GET_QUESTION_URL = "$SERVER_URL/?op=getquestion"

    const val GET_ANSWER_URL = "$SERVER_URL/?op=getanswer"

    const val GET_TIME_URL = "$SERVER_URL/?op=gettime"

    const val SET_TIME_URL = "$SERVER_URL/?op=settime"

    const val SET_ANSWER_URL = "$SERVER_URL/?op=setanswer"

}