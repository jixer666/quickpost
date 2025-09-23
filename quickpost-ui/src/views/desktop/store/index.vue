<template>
  <div class="desktop-main-div">
    <!-- 主卡片 -->
    <el-card
      class="file-box"
      @mouseenter="isCardHover = true"
      @mouseleave="isCardHover = false"
    >
      <div class="box-top">
        <!-- Logo 区域 -->
        <div class="logo" @click="handleLogoClick">
          <div
            class="inner-logo"
            :class="{ 'logo-active': isLogoActive }"
          ></div>
        </div>
        <!-- 标题 -->
        <div class="title"><span>快传驿站</span></div>
      </div>

      <!-- 表单区域 -->
      <el-form :model="form" ref="form" label-width="auto" class="code-form" @submit.prevent :rules="formRules">
        <el-form-item>
          <FileUpload :isDrag="true" :fileType="[]" class="upade-div" :fileSize="50" ref="fileUploadRef" :limit="1"></FileUpload>
        </el-form-item>

        <el-form-item prop="validTimeType">
          <el-select v-model="form.validTimeType" placeholder="有效期">
            <el-option :label="item.label" :value="item.value" v-for="(item, index) in pack_valid_time_type" :key="index"/>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button
              class="submit-btn"
              type="primary"
              :loading="isSubmitting"
              @click="handleSubmit"
          >
            存放文件
          </el-button>
        </el-form-item>

      </el-form>

      <!-- 底部提示文字 -->
      <p class="tips">
        <el-link class="link" @click="$router.push('/desktop/pickup')"
          >前往提取文件</el-link
        >
      </p>
    </el-card>
  </div>
</template>

<script setup>
const { proxy } = getCurrentInstance()
const { pack_valid_time_type } = proxy.useDict("pack_valid_time_type")
</script>

<script>
import FileUpload from "@/components/MyFileUpload";
import {addPack} from "@/api/app/pack.js";
import { ElNotification } from 'element-plus'

export default {
  name: "desktop-store",
  components: {
    FileUpload,
  },
  data() {
    return {
      form: { validTimeType: null },
      isInputFocus: false,
      isCardHover: false,
      isLogoActive: false,
      isSubmitting: false,
      formRules: {
        validTimeType: [{ required: true, trigger: "blur", message: "请选择有效期" }]
      },
    };
  },
  methods: {
    // Logo 点击动效
    handleLogoClick() {
      this.isLogoActive = true;
      setTimeout(() => (this.isLogoActive = false), 500);
    },
    // 提交逻辑（模拟加载状态）
    handleSubmit() {
      this.$refs.form.validate(valid => {
        if (!valid) {
          return;
        }
        const fileList = this.$refs.fileUploadRef.fileList
        if (!fileList || fileList.length === 0) {
          this.$message.warning("文件未上传");
        }
        this.isSubmitting = true;
        addPack({
          validTimeType: this.form.validTimeType,
          fileIds:  fileList.map(file => file.fileId)
        }).then(res => {
          ElNotification({
            title: "文件存放成功",
            dangerouslyUseHTMLString: true,
            message: `取件码为：<strong>${res.data.code}</strong>`,
            type: 'success',
            duration: 2000,
          })
          this.isSubmitting = false;
          this.clearForm();
        }).catch(error => {
          this.isSubmitting = false;
        })
      })
    },
    clearForm() {
      this.form.validTimeType = null; // 清空有效期下拉选择
      this.$refs.form.clearValidate(); // 清除表单校验提示（避免残留红色边框/错误信息）

      const fileUploadRef = this.$refs.fileUploadRef;
      if (fileUploadRef) {
        fileUploadRef.fileList = [];

        const elUploadRef = fileUploadRef.$refs.imageUpload;
        if (elUploadRef && elUploadRef.clearFiles) {
          elUploadRef.clearFiles(); // 清除所有上传相关的文件记录（包括未提交的临时文件）
        }
      }

      this.isSubmitting = false;
    }
  },
};
</script>

<style scoped>
/* 页面容器：渐变背景+居中 */
.desktop-main-div {
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
  width: 70px;
  height: 70px;
  border-radius: 20px;
  margin-right: 20px;
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
  letter-spacing: 1px;
  display: flex;
  justify-content: center;
  align-items: center;
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
.box-top{
    display: flex;
    align-items: center;
    justify-content: center;
    margin-bottom: 20px;
}
.upade-div{
    width: 100%;
}
</style>
