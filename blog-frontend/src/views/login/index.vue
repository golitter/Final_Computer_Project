<template>
    <div class="login-container">
        <div class="login-wrapper">
            <div class="login-header">
                <h1>Golemon Blog</h1>
                <p>Share Technology, Record Life</p>
            </div>
            
            <div class="login-form-wrapper">
                <h2>Welcome Back</h2>
                <el-form ref="loginForms" :model="ruleForm" :rules="rules" label-width="0px" class="login-form">
                    <el-form-item prop="username">
                        <el-input 
                            v-model="ruleForm.username" 
                            placeholder="Username" 
                            :prefix-icon="User"
                            class="custom-input"
                        />
                    </el-form-item>

                    <el-form-item prop="password">
                        <el-input 
                            v-model="ruleForm.password" 
                            type="password" 
                            placeholder="Password" 
                            show-password 
                            :prefix-icon="Lock"
                            class="custom-input"
                        />
                    </el-form-item>

                    <div class="form-options">
                        <el-checkbox v-model="remember" label="Remember me" />
                        <router-link to="/forgot-password" class="forgot-password">Forgot Password?</router-link>
                    </div>

                    <el-form-item>
                        <self-button @click="submitForm" class="login-button">Login</self-button>
                    </el-form-item>

                    <div class="register-hint">
                        Don't have an account?
                        <router-link to="/register" class="register-link">Register Now</router-link>
                    </div>
                </el-form>
            </div>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { reactive, ref, nextTick } from 'vue';
import { User, Lock } from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';
import { login } from '@/api/user';
// @TODO: Why does it report an error without adding the .ts suffix?
import { setToken, setUserInfo, removeToken, removeUserInfo, getToken, getUserInfo } from '@/utils/storage';
import { md5Encrypt } from '@/utils/encrypt';
import router from '@/router';
import FormCard from '@/components/FormCard.vue';
import SelfButton from '@/components/SelfButton.vue';
import { useAdminStore } from '@/stores/admin';

// Clear old token and user information
removeToken();
removeUserInfo();

const loginForms = ref();
const ruleForm = reactive({
    username: '',
    password: '',
});
const remember = ref(false);  // Remember me

const rules = reactive({
    username: [
        { required: true, message: 'Username cannot be empty', trigger: 'change' },
    ],
    password: [
        { required: true, message: 'Password cannot be empty', trigger: 'change' },
    ],
});

const adminStore = useAdminStore();

const submitForm = () => {
    if (!loginForms.value) return;

    loginForms.value.validate((valid: boolean) => {
        if (!valid) {
            ElMessage.error('Username and password cannot be empty');
            return;
        }
        login(ruleForm.username, md5Encrypt(ruleForm.password)).then((data) => {
            console.log(data);
            let status = data.status;
            if (status === 200 && data.data) {
                setToken(data.data.token);
                setUserInfo(data.data.userInfo);
                ElMessage.success('Login successful');
                // Update admin status
                adminStore.updateIsAdmin();
                // Ensure admin status is updated
                if (adminStore.$state.isAdmin) {
                    console.log('token', getToken());
                    router.replace('/').then(() => {
                        // Use nextTick to ensure state is updated before refresh
                        nextTick(() => {
                            window.location.reload();
                        });
                    }).catch(err => {
                        console.error('Route navigation failed:', err);
                        ElMessage.error('Navigation failed, please refresh manually');
                    });
                } else {
                    ElMessage.error('Login status update failed, please login again');
                }
            } else {
                ElMessage.error(data.message || 'Login failed');
            }
        });
    });
};
</script>

<style lang="scss" scoped>
.login-container {
    min-height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    background: #f8f9fa;
    padding: 20px;
}

.login-wrapper {
    width: 100%;
    max-width: 420px;
    background: white;
    border-radius: 16px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
    overflow: hidden;
    padding: 40px;
}

.login-header {
    text-align: center;
    margin-bottom: 40px;

    h1 {
        font-size: 2.5rem;
        color: #2c3e50;
        margin-bottom: 10px;
        font-weight: 700;
    }

    p {
        color: #6c757d;
        font-size: 1.1rem;
    }
}

.login-form-wrapper {
    h2 {
        font-size: 1.8rem;
        color: #2c3e50;
        margin-bottom: 30px;
        text-align: center;
        font-weight: 600;
    }
}

.login-form {
    .custom-input {
        :deep(.el-input__wrapper) {
            padding: 12px 15px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
            transition: all 0.3s ease;
            background: #f8f9fa;

            &:hover, &:focus-within {
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                background: white;
            }
        }
    }

    .form-options {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 25px;

        .forgot-password {
            color: #3498db;
            text-decoration: none;
            font-size: 0.9rem;
            transition: color 0.3s ease;

            &:hover {
                color: #2980b9;
            }
        }
    }

    .login-button {
        width: 100%;
        padding: 12px;
        font-size: 1rem;
        border-radius: 8px;
        background: #3498db;
        border: none;
        color: white;
        cursor: pointer;
        transition: all 0.3s ease;
        font-weight: 500;

        &:hover {
            background: #2980b9;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(52, 152, 219, 0.3);
        }
    }
}

.register-hint {
    text-align: center;
    margin-top: 25px;
    color: #6c757d;
    font-size: 0.9rem;

    .register-link {
        color: #3498db;
        text-decoration: none;
        font-weight: 500;
        margin-left: 5px;
        transition: color 0.3s ease;

        &:hover {
            color: #2980b9;
        }
    }
}

@media (max-width: 480px) {
    .login-wrapper {
        padding: 30px 20px;
    }

    .login-header {
        h1 {
            font-size: 2rem;
        }

        p {
            font-size: 1rem;
        }
    }

    .login-form-wrapper {
        h2 {
            font-size: 1.5rem;
        }
    }
}
</style>
