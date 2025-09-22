<template>
  <div class="desktop-pickup-div">
    <!-- 主卡片 -->
    <el-card class="file-box" @mouseenter="isCardHover = true" @mouseleave="isCardHover = false">
      <!-- Logo 区域 -->
      <div class="logo" @click="handleLogoClick">
        <div class="inner-logo" :class="{ 'logo-active': isLogoActive }"></div>
      </div>
      
      <!-- 标题 -->
      <h1 class="title">快传驿站</h1>
      
      <!-- 表单区域 -->
      <el-form :model="form" label-width="auto" class="code-form" @submit.prevent>
        <el-form-item>
          <!-- 取件码输入框（带聚焦/失焦动效） -->
          <el-input 
            v-model="form.name" 
            placeholder="请输入6位取件码" 
            class="input-code"
            @focus="isInputFocus = true"
            @blur="isInputFocus = false"
            :class="{ 'input-focus': isInputFocus }"
            maxlength="6"
            show-word-limit
            @keyup.enter.native="handleSubmit"
          />
        </el-form-item>
        
        <el-form-item>
          <el-button
            class="submit-btn" 
            type="primary"
            :loading="isSubmitting"
            @click="handleSubmit"
          >
            提取文件
          </el-button>
        </el-form-item>
      </el-form>
      
      <!-- 底部提示文字 -->
      <p class="tips"><el-link class="link" @click="toPage('/desktop/store')">前往提取文件</el-link></p>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "desktop-pickup",
  data() {
    return {
      form: { name: "" }, // 初始化输入框绑定值，避免undefined
      isInputFocus: false, // 输入框聚焦状态
      isCardHover: false, // 卡片 hover 状态
      isLogoActive: false, // Logo 点击动效状态
      isSubmitting: false // 按钮加载状态
    };
  },
  methods: {
    toPage(url) {
      console.log(11)
      this.$router.push(url);
    },
    // Logo 点击动效
    handleLogoClick() {
      this.isLogoActive = true;
      setTimeout(() => (this.isLogoActive = false), 500);
    },
    // 提交逻辑（模拟加载状态）
    handleSubmit() {
      if (!this.form.name) {
        this.$message.warning("请输入取件码");
        return;
      }
      // 模拟接口请求加载
      this.isSubmitting = true;
      setTimeout(() => {
        this.isSubmitting = false;
        this.$message.success("文件提取中...");
        // 这里可添加实际跳转/文件处理逻辑
      }, 1500);
    }
  }
};
</script>

<style scoped>
/* 页面容器：渐变背景+居中 */
.desktop-pickup-div {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  /* 柔和渐变背景，替代纯色 */
  background: linear-gradient(135deg, #f0f5ff 0%, #d6e4ff 100%);
  padding: 20px;
}

/* 主卡片：hover 浮起动效 */
.file-box {
  background-color: #fff;
  border-radius: 16px; /* 更大圆角更柔和 */
  width: 420px;
  text-align: center;
  padding: 40px 30px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease; /* 统一过渡动效 */
  border: none;
}
.file-box:hover {
  box-shadow: 0 12px 32px rgba(48, 105, 242, 0.15);
}

/* Logo 样式：蓝色文件图标 */
.logo {
  width: 80px;
  height: 80px;
  margin: 0 auto 25px;
  border-radius: 20px;
  background-color: #e6f0ff;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  transition: background-color 0.3s ease;
}
.logo:hover {
  background-color: #d1e0ff;
}
.inner-logo {
  width: 40px;
  height: 48px;
  background-color: #3065f2;
  border-radius: 8px;
  position: relative;
  transition: transform 0.5s ease;
}
/* Logo 点击动效 */
.logo-active {
  transform: scale(0.9) rotate(5deg);
}
/* Logo 内部细节（模拟文件图标） */
.inner-logo::before {
  content: "";
  position: absolute;
  top: 8px;
  left: 8px;
  width: 24px;
  height: 6px;
  background-color: #fff;
  border-radius: 3px;
}
.inner-logo::after {
  content: "";
  position: absolute;
  top: 22px;
  left: 8px;
  width: 24px;
  height: 16px;
  background-color: #fff;
  border-radius: 3px;
}

/* 标题样式 */
.title {
  font-size: 24px;
  font-weight: 600;
  color: #1d2129;
  margin-bottom: 35px;
  letter-spacing: 1px;
}

/* 表单容器 */
.code-form {
  width: 100%;
  margin-bottom: 25px;
}

/* 输入框样式：聚焦动效 */
.input-code {
  height: 50px;
  font-size: 16px;
  border-radius: 10px !important;
  border: 1px solid #e5e6eb !important;
  transition: all 0.3s ease;
}
/* 输入框聚焦状态 */
.input-focus {
  border-color: #3065f2 !important;
  box-shadow: 0 0 0 3px rgba(48, 105, 242, 0.15) !important;
}
/* 输入框占位符样式 */
.input-code::placeholder {
  color: #c9cdcf;
  font-size: 14px;
}

/* 提交按钮样式：强化视觉 */
.submit-btn {
  width: 100%;
  height: 50px;
  font-size: 16px;
  border-radius: 10px !important;
  background-color: #3065f2 !important;
  border: none !important;
  transition: background-color 0.3s ease;
}
.submit-btn:hover {
  background-color: #2554d0 !important; /* 加深hover色 */
}
.submit-btn:active {
  background-color: #1e46b3 !important; /* 点击反馈色 */
}

/* 底部提示文字 */
.tips {
  font-size: 13px;
  color: #86909c;
  margin: 0;
}
/* 链接样式 */
.link {
  color: #3065f2;
  text-decoration: none;
}
</style>