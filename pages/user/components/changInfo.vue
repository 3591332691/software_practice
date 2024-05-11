<template>
	<view class="viewport">
		<view class="form">
			<view class="item">
				<text class="label">账号</text>
				<text class="account">1234</text>
			</view>
			<view class="item">
				<text class="label">昵称</text>
				<input class="input" type="text" placeholder="请填写昵称" value="123456" />
			</view>
			<view class="item">
				<text class="label">性别</text>
				<radio-group>
					<label class="radio">
						<radio value="男" color="#61ba8b" />
						男
					</label>
					<label class="radio">
						<radio value="女" color="#61ba8b"/>
						女
					</label>
				</radio-group>
			</view>
			<view class="item">
				<text class="label">生日</text>
				<input class="input" type="text" placeholder="请填写生日" value="01-01"/>
			</view>
			<button @tap="onSubmit" class="button">保 存</button>
		</view>
	</view>
</template>

<script>
</script>

<style lang="scss">
	.form {
	    margin: 20rpx 20rpx 0;
		
	  .item {
	    display: flex;
	    height: 100rpx;
	    line-height: 100rpx;
	    font-size: 28rpx;
	    border-bottom: 1rpx solid #ddd;
	
	    &:last-child {
	      border: none;
	    }
	
	    .label {
	      width: 180rpx;
	      color: #333;
	    }
	
	    .account {
		  color: #808080;
	    }
	
	    .input {
	      flex: 1;
	      height: 100rpx;
	    }
	
	    .radio {
	      margin-right: 20rpx;
	    }
	
	  }
	
	  .button {
		margin-top: 30rpx;
	    height: 80rpx;
	    text-align: center;
	    line-height: 80rpx;
	    color: #fff;
	    border-radius: 80rpx;
	    font-size: 30rpx;
	    background-color: #61ba8b;
	  }
	}
</style>