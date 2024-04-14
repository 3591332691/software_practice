<template>
  <view class="nav">
    <view :style="'height:' + status + 'rpx;' + containerStyle"></view>
    <view v-if="isHome" class="headNav"
      :style="'height:' + navHeight + 'rpx;line-height:' + navHeight +'rpx;padding-left:20rpx;' + 'background:' + props.background">
      <text class="app-title">阅读平台</text>
      <view style="flex: 1">
        <navigator url="../../pages/search/index" :style="
    				'height:' +
    				menu.height*2 +
    				'rpx;line-height:' +
    				menu.height*2 +
    				'rpx;margin-top:' + 
    				(menu.top*2 - status) +
    				'rpx;margin-left:32rpx;margin-right:' +
    				(menu.width*2 + 24) + 
    				'rpx;background: #f4f4f4;border-radius:200rpx;text-align:center'
    			">
          <text class="search-text">找小说</text>
        </navigator>
      </view>
    </view>
    <view v-else class="navbar" :style="'height:' + navHeight + 'rpx;' + containerStyle">
      <view class="back-icon" @click="backOrHome">
        <image v-if="pages > 1" src="../../static/navbar/ic_back.png"></image>
        <image v-else src="../../static/navbar/ic_home.png"></image>
      </view>
      <view class="nav-title" v-if="titleText">
        <view :style="'height:' + navHeight + 'rpx; line-height:' + navHeight + 'rpx;' + textStyle">{{titleText}}</view>
      </view>
    </view>
  </view>
</template>

<script setup>
  import {
    ref,
    onBeforeMount,
    reactive,
    defineProps
  } from 'vue'

  const props = defineProps({
    background: {
      type: String,
      default: 'rgba(255, 255, 255, 1)'
    },
    color: {
      type: String,
      default: 'rgba(0, 0, 0, 1)'
    },
    fontSize: {
      type: String,
      default: 32
    },
    iconWidth: {
      type: String,
      default: 116
    },
    iconHeight: {
      type: String,
      default: 32
    },
    titleText: {
      type: String,
      default: ''
    },
    isHome: {
      type: Boolean,
      default: false
    }
  })

  onBeforeMount(() => {
    setNavSize()
    setStyle()
    uni.$emit('updateNavbarHeight',{height: status.value + navHeight.value})
  })
  // 状态栏高度
  const status = ref(0)
  // 内部高度
  const navHeight = ref(0)
  // 背景颜色
  const containerStyle = ref('')
  // 字体样式
  const textStyle = ref('')
  // 图标样式
  const iconStyle = ref('')
  // 页面栈中页面数量
  const pages = ref(getCurrentPages().length)
  // 获取到胶囊的位置
  const menu = reactive(uni.getMenuButtonBoundingClientRect())

  // 计算状态栏高度
  const setNavSize = () => {
    const {
      system,
      statusBarHeight
    } = uni.getSystemInfoSync()

    status.value = statusBarHeight * 2
    const isiOS = system.indexOf('iOS') > -1
    if (!isiOS) {
      navHeight.value = 96
    } else {
      navHeight.value = 88
    }
  }

  // 计算样式
  const setStyle = () => {
    containerStyle.value = ['background:' + props.background].join(';')
    textStyle.value = ['color:' + props.color, 'font-size:' + props.fontSize + 'rpx'].join(';')
    iconStyle.value = ['width:' + props.iconWidth + 'rpx', 'height:' + props.iconHeight + 'rpx'].join(';')

  }

  // 页面跳转
  const backOrHome = () => {
    if (pages.value > 1) {
      uni.navigateBack()
    } else {
      uni.switchTab({
        url: '/pages/index/index'
      })
    }
  }

  // 样式设计
</script>

<style>
  .nav {
    position: fixed;
    width: 100%;
    top: 0;
    left: 0;
    z-index: 2;
  }

  .back-icon {
    display: flex;
    align-items: center;
    width: 64rpx;
    height: 100%;
    margin-left: 20rpx;
  }

  .back-icon image {
    width: 64rpx;
    height: 64rpx;
  }

  .navbar {
    position: relative;
  }

  .nav-title {
    position: absolute;
    top: 0;
    left: 50%;
    transform: translate(-50%);
  }

  .headNav {
    display: flex;
  }

  .app-title {
    display: inline-block;
    position: relative;
    font-size: 30rpx;
    font-weight: bold;
    padding-left: 55rpx;
    background: url("../../static/images/novel.png") no-repeat left center;
    /* background-image: "/static/images/novel.png" ; */
    background-size: 40rpx;
  }

  .city:after {
    content: ' ';
    display: inline-block;
    height: 6px;
    width: 6px;
    border-width: 1px 1px 0 0;
    border-color: #353535;
    border-style: solid;
    -webkit-transform: matrix(0.71, 0.71, -0.71, 0.71, 0, 0);
    transform: matrix(0.71, 0.71, -0.71, 0.71, 0, 0);
    position: relative;
    top: -2px;
    position: absolute;
    top: 50%;
    margin-top: -4px;
    right: -10px;
  }

  .search-text {
    display: inline-block;
    padding-left: 30rpx;
    color: #bbbbbb;
    font-size: 26rpx;
    background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIAAAAyCAYAAAAeP4ixAAAABGdBTUEAAK/INwWK6QAAABl0RVh0U29mdHdhcmUAQWRvYmUgSW1hZ2VSZWFkeXHJZTwAAAPWSURBVHja1FpNSFRRFH4+Zikig4sQkUeEtAgrk4EMCXFCiwhjoN9FrdpIixYREbMIEYkWLUKCCGqjaTUkEbUYXYSMyWASLUJC4hExuJBw4TKkc/IMXYY592fevY/XgY+Bd//eN+ece35mmorFomdJAuZ56DmUbDb79zMV4aV9QAYwAOgFdAGaa+ZtA9YAq4AFwApgxwW5VAMEOgCjgAsa85uJJOIaPZsGTAIqNgn5BgR6AI8Ai5okOLkEKBGZHolJWicS0It/AgxbtIZTtOc5G2RURPaRFiYc+us9wEM6ywmR/YDXlrXAyWlAgc606uz47bwEHNDY4xdgjnxnA/CDnncC9gD6ASOAtGKfbsAsIAdYt0EkIFWrSOBVegfwjbl9vtLnM8AYXc/jdIPJyDwAXDe90VKMY8vMCePAZcCywWEhAUn3AaYUZvYB8MqETK2PpBWO/RNwFDDTYAzANUuAI4Dvknn3NUyRJRKQ6jlB2x8ElCM6dkiR/qTgT/Xkrsm1LBLpUJhUjkzDluBeZwG/JSbWbkokoLSDk4uATQfXLmrmimR8VFcrvvDJpR1lQ8c2lSUixKUzvgmRjGRO3nEqHtI1zkmvCZFBZnzTsl9wgqn+FjM2YEKkhxl/47owErQyF1UjAUXderLoxSfcWV06Du8LBRAXAOMS7qwWG2n8RoxEKnFUiHGI73Jxe4xE2m0Q2U4wkS1dIqEkVvTHSOS4JMaEuhpZYcZHbHU5NBocZyQpjLZpLTDjaUmMsSlYq7cyYyUTIiuSOeOOtRJIirkdSUJZlwgumJakCH2OyAS09yFm/IluiuQLuc6kZB7W2G0OiLQp6venjcQRjKzvJHMLlv2li/bkZMYks/BrMtC8ZG4nXQoZC+aE2fZ72pOTiShdFGy23ZLMx7r+I1VuQQSfwJ7vXo2bLGiUCH4DLwBvFWumyCyGNQ8LaG5B4ROizJL2tcikmCLnBqm9W7K2ah5b3r+WaUVIxzso7ai2TFsb0CCSOS+8lxERlHXaYFZBxqMXvEpwIVpkfEUdnVOYWVyiNDNVGo+awYbyzaST0SlmUJ3YUD5sWTsYJw4KZhOJjG5VhmQ+k3aOSdIZlWB79LG32wi/Dfji7TYAI5Mx/VU3JFQoeGIeJv483VKnKFqjVLxECWBYZ09PuFwaugCaEvSHgWrELxiciT3p5Ww2G6Ys2nxoab2JZp4DhnBtkrooVTKmPjM2Pz8fJI2ISCanOT8TuZfkmMyqAZnEEhHJqMysnHQiuj6Tx1sr6UREMkM1TRJ8dsKjnlzK+z+kGohrG4lh9Y9nfwQYAJts3SyOpWPCAAAAAElFTkSuQmCC) no-repeat left center;
    background-size: 23rpx;
  }
</style>