<script setup>
	function previewImage() {
	  var preview = document.querySelector('img');
	  var file = document.querySelector('input[type=file]').files[0];
	  var reader = new FileReader();
	
	  reader.onloadend = function () {
	    preview.src = reader.result;
	  }
	
	  if (file) {
	    reader.readAsDataURL(file);
	  } else {
	    preview.src = "";
	  }
	}
</script>

<template>
	<view class="viewport">
		<view class="form">
			<view class="item">
				<text class="label">书名</text>
				<input class="input" type="text" placeholder="请填写书名" value="123456" />
			</view>
			
			<view class="item">
				<text class="label">作者</text>
				<text class="account">1234</text>
			</view>
	
			<view class="item">
				<text class="label">简介</text>
				<textarea class="area" name="" id="" cols="30" rows="10"></textarea>
			</view>
			
			<view class="item">
				<text class="label">封面</text>
				<input type="file" onchange="previewImage()" accept="image/*">
				<img src="" alt="预览图片" style="max-width: 300px; max-height: 300px;">
			</view>
			
			<view class="item">
				<text class="label">类别</text>
				<text class="account">1234</text> 
				<!-- 待写选择框 -->
			</view>
			<button @tap="onSubmit" class="button">上传</button>
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
		padding: 30rpx 0;
	    font-size: 28rpx;
	    border-bottom: 1rpx solid #ddd;
	
	    &:last-child {
	      border: none;
	    }
	
	    .label {
	      width: 100rpx;
	      color: #333;
	    }
	
	    .account {
		  color: #808080;
	    }
	
	    .input {
	      flex: 1;
	    }
		
		.area {
			flex: 1;
			border: 1rpx solid #ddd;
			line-height: 50rpx;
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