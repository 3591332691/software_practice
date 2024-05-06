<template>
  <view>
    <h1>个人信息页面</h1>
	<p>用户名:{{ UserName }}</p>
  </view>
  
</template>

<script></script>

<script>
import { getUserInfo } from '../../common/js/plugin.js';

export default {
  data() {
    return {
      UserName: 'aa' // 初始化用户名为空字符串
    };
  },
  mounted() {
    // 在页面加载完成后，获取用户信息
    this.fetchUserInfo();
  },
  methods: {
    fetchUserInfo() {
      // 使用之前封装的函数获取用户信息，假设函数名为getUserInfo
      getUserInfo(1)
        .then(userInfo => {
		  console.log(userInfo);
          // 将获取到的用户信息赋值给 userInfo
          try {
             this.UserName = userInfo.Name;
          } catch (error) {
              console.error('Error parsing JSON:', error);
          }
        })
        .catch(error => {
          console.error('Failed to fetch user info:', error);
        });
    }
  }
};
</script>

<style>

</style>
