class Utils {
  // 获取用户信息
  getUserInfo() {
    // 调用登录的api
    uni.login({
      success: function(res) {
        console.log(res)
      }
    })
  }
}

export default new Utils