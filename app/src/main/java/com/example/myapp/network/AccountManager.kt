package com.example.myapp.network

import android.util.Log
import com.example.myapp.model.AccountDevices
import com.example.myapp.model.AccountRequest
import com.example.myapp.model.AccountUpdate
import com.example.myapp.model.AreaAdd
import com.example.myapp.model.AreaInfo
import com.example.myapp.model.AreaRename
import com.example.myapp.model.HouseCreate
import com.example.myapp.model.HouseInfo
import com.example.myapp.model.HouseJoin
import com.example.myapp.model.Jwt
import com.example.myapp.model.Member
import com.example.myapp.model.UserInfo

object AccountManager {
    var token: String? = null
}

suspend fun AccountManager.login(username: String, password: String): Jwt? {
    try {
        val response = api.login(AccountRequest(username, password))
        if (response.code != 200) {
            return null
        }
        token = response.data?.token
        return response.data
    } catch (e: Exception) {
        Log.e("Login", "failed", e)
        return null
    }
}

suspend fun AccountManager.auth(token: String): Boolean {
    try {
        this.token = token
        return apiWithToken.auth().code == 200
    } catch (e: Exception) {
        Log.e("Auth", "http error", e)
        this.token = null
        return false
    }
}

suspend fun AccountManager.signup(username: String, password: String): Boolean {
    try {
        api.signup(AccountRequest(username, password))
        return true
    } catch (e: Exception) {
        Log.e("signup", e.toString())
        return false
    }
}

suspend fun AccountManager.fetchData(): AccountDevices? {
    try {
        val response = apiWithToken.getAllAccountDevices()
        if (response.code != 200) {
            return null
        }
        return response.data
    } catch (e: Exception) {
        Log.e("Fetch Data", e.message, e)
        return null
    }
}

suspend fun AccountManager.fetchUserInfo(): UserInfo? {
    try {
        val response = apiWithToken.getUserInfo()
        if (response.code == 200) {
            return response.data
        }
        Log.e("fetchUserInfo", response.message)
        return null
    } catch (e: Exception) {
        Log.e("fetchUserInfo", e.message, e)
        return null
    }
}


suspend fun AccountManager.updateUserInfo(userInfo: UserInfo): Boolean {
    try {
        val response = apiWithToken.newUserInfo(userInfo)
        if (response.code == 200) {
            return true
        }
        Log.e("updateUserInfo", response.message)
        return false
    } catch (e: Exception) {
        Log.e("updateUserInfo", e.message, e)
        return false
    }
}

suspend fun AccountManager.fetchMemberInfo(): Member? {
    try {
        val response = apiWithToken.getMemberInfo()
        if (response.code == 200) {
            return response.data
        }
        Log.e("fetchMemberInfo", response.message)
        return null
    } catch (e: Exception) {
        Log.e("fetchMemberInfo", e.message, e)
        return null
    }
}

suspend fun AccountManager.fetchAreasInfo(): List<AreaInfo>? {
    try {
        val response = apiWithToken.getAreasInfo()
        if (response.code == 200) {
            return response.data
        }
        Log.e("fetchAreasInfo", response.message)
        return null
    } catch (e: Exception) {
        Log.e("fetchAreasInfo", e.message, e)
        return null
    }
}

suspend fun AccountManager.createArea(houseId: Int, areaName: String): Boolean {
    try {
        val response = apiWithToken.createArea(addArea = AreaAdd(houseId, areaName))
        if (response.code == 200) {
            return true
        }
        Log.e("createArea", response.message)
        return false
    } catch (e: Exception) {
        Log.e("createArea", e.message, e)
        return false
    }
}

suspend fun AccountManager.deleteArea(accountId: Int): Boolean {
    try {
        val response = apiWithToken.deleteArea(accountId)
        if (response.code == 200) {
            return true
        }
        Log.e("deleteArea", response.message)
        return false
    } catch (e: Exception) {
        Log.e("deleteArea", e.message, e)
        return false
    }
}

suspend fun AccountManager.renameArea(areaId: Int, areaRename: AreaRename): Boolean {
    try {
        val response = apiWithToken.renameArea(areaId, areaRename)
        if (response.code == 200) {
            return true
        }
        Log.e("updateAreaName", response.message)
        return false
    } catch (e: Exception) {
        Log.e("updateArea", e.message, e)
        return false
    }
}

suspend fun AccountManager.fetchHouseInfo(): List<HouseInfo>? {
    try {
        val response = apiWithToken.getHouseInfo()
        if (response.code == 200) {
            return response.data
        }
        Log.e("fetchHouseInfo", response.message)
        return null
    } catch (e: Exception) {
        Log.e("fetchHouseInfo", e.message, e)
        return null
    }
}

suspend fun AccountManager.joinHouse(accountId: Int, houseId: Int): Boolean {
    try {
        Log.e("addHousesInfo", accountId.toString() + "  " + houseId.toString())
        val response = apiWithToken.joinHouse(
            houseJoin =
            HouseJoin(
                accountId = accountId,
                houseId = houseId
            )
        )
        if (response.code == 200) {
            return true
        }
        Log.e("addHousesInfo", response.message)
        return false
    } catch (e: Exception) {
        Log.e("addHouseInfo", e.message, e)
        return false
    }
}

suspend fun AccountManager.crateHouse(houseName: String): Boolean {
    try {
        val response = apiWithToken.createHouse(houseCreate = HouseCreate(houseName))
        if (response.code == 200) {
            return true
        }
        Log.e("createHousesInfo", response.message)
        return false
    } catch (e: Exception) {
        Log.e("createHouseInfo", e.message, e)
        return false
    }
}

suspend fun AccountManager.deleteHouse(houseId: Int): Boolean {
    try {
        val response = apiWithToken.deleteHouse(houseId)
        if (response.code == 200) {
            return true
        }
        Log.e("deleteHouse", response.message)
        return false
    } catch (e: Exception) {
        Log.e("deleteHouse", e.message, e)
        return false
    }
}

suspend fun AccountManager.updateAccount(
    oldPassword: String,
    newPassword: String?,
    username: String?
): Boolean {
    try {
        val response = apiWithToken.updateAccount(
            newAccountInfo = AccountUpdate(
                oldPassword,
                newPassword,
                username
            )
        )
        if (response.code == 200) {
            return true
        }
        Log.e("updateAccountInfo", response.message)
        return false
    } catch (e: Exception) {
        Log.e("updateAccountInfo", e.message, e)
        return false
    }
}