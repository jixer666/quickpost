<template>
  <div>
    <div class="navbar">
      <div class="logo" @click="handleLogoClick">
        <div class="inner-logo" :class="{ 'logo-active': isLogoActive }"></div>
      </div>
      <div class="user-actions">
        <el-button type="info" round>
          <el-icon><QuestionFilled /></el-icon>
          帮助
        </el-button>
      </div>
    </div>
    <router-view />
  </div>
</template>

<script>
import { generatedAnonymousUser } from "@/api/app/anoymousUser.js";
import {getAnonymousToken, setAnonymousToken} from '@/utils/auth'

export default {
  name: "desktop",
  data() {
    return {
      isLogoActive: false,
    };
  },
  mounted() {
    this.getAnonymousUser();
  },
  methods: {
    getAnonymousUser() {
      if (getAnonymousToken()) {
        return
      }
      generatedAnonymousUser().then(res => {
        setAnonymousToken(res.data.token);
      });
    },
    handleLogoClick() {
      this.isLogoActive = true;
      setTimeout(() => (this.isLogoActive = false), 500);
    },
  },

};
</script>

<style scpoed>
.navbar {
  width: 100%;
  position: fixed;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 15px 30px;
  background-color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.logo {
  width: 40px;
  height: 40px;
  border-radius: 10px;
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
  width: 20px;
  height: 24px;
  background-color: #3065f2;
  border-radius: 4px;
  position: relative;
  transition: transform 0.5s ease;
}

.logo-active {
  transform: scale(0.9) rotate(5deg);
}

.inner-logo::before {
  content: "";
  position: absolute;
  top: 4px;
  left: 4px;
  width: 12px;
  height: 3px;
  background-color: #fff;
  border-radius: 1.5px;
}

.inner-logo::after {
  content: "";
  position: absolute;
  top: 11px;
  left: 4px;
  width: 12px;
  height: 8px;
  background-color: #fff;
  border-radius: 1.5px;
}
</style>
