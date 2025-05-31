<template>
    <div class="register-container">
        <div class="register-wrapper">
            <div class="register-header">
                <h1>Golemon Blog</h1>
                <p>Share technology, record life</p>
            </div>
            
            <div class="register-form-wrapper">
                <h2>Create Account</h2>
                <el-form ref="registerForms" :model="ruleForm" :rules="rules" label-width="0px" class="register-form">
                    <el-form-item prop="username">
                        <el-input 
                            v-model="ruleForm.username" 
                            placeholder="Username" 
                            :prefix-icon="User"
                            class="custom-input"
                        />
                    </el-form-item>

                    <el-form-item prop="email">
                        <el-input 
                            v-model="ruleForm.email" 
                            placeholder="Email" 
                            :prefix-icon="Message"
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

                    <el-form-item prop="confirm">
                        <el-input 
                            v-model="ruleForm.confirm" 
                            type="password" 
                            placeholder="Confirm Password" 
                            show-password 
                            :prefix-icon="Lock"
                            class="custom-input"
                        />
                    </el-form-item>

                    <el-form-item>
                        <self-button @click="submitForm" class="register-button">Register</self-button>
                    </el-form-item>

                    <div class="login-hint">
                        Already have an account?
                        <router-link to="/login" class="login-link">Login Now</router-link>
                    </div>
                </el-form>
            </div>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue';
import { User, Lock, Message } from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';
import { register } from '@/api/user';
import { setToken, setUserInfo } from '@/utils/storage';
import { md5Encrypt } from '@/utils/encrypt';
import router from '@/router';
import SelfButton from '@/components/SelfButton.vue';
import { useAdminStore } from '@/stores/admin';

const registerForms = ref();
const ruleForm = reactive({
    username: '',
    email: '',
    password: '',
    confirm: '',
});

const rules = reactive({
    username: [
        { required: true, message: 'Username cannot be empty', trigger: 'change' },
        { min: 3, max: 20, message: 'Username length must be between 3 and 20 characters', trigger: 'change' }
    ],
    email: [
        { required: true, message: 'Email cannot be empty', trigger: 'change' },
        { type: 'email', message: 'Please enter a valid email address', trigger: 'change' }
    ],
    password: [
        { required: true, message: 'Password cannot be empty', trigger: 'change' },
        { min: 6, max: 20, message: 'Password length must be between 6 and 20 characters', trigger: 'change' }
    ],
    confirm: [
        { required: true, message: 'Please enter password again', trigger: 'change' },
        {
            validator: (rule: any, value: string, callback: any) => {
                if (value !== ruleForm.password) {
                    callback(new Error('Passwords do not match'));
                } else {
                    callback();
                }
            },
            trigger: 'change'
        }
    ],
});

const adminStore = useAdminStore();

const submitForm = () => {
    if (!registerForms.value) return;

    registerForms.value.validate((valid: boolean) => {
        if (!valid) {
            ElMessage.error('Please check your input');
            return;
        }
        register(ruleForm.username, md5Encrypt(ruleForm.password), ruleForm.email).then((data) => {
            console.log(data);
            let status = data.status;
            if (status === 200 && data.data) {
                setToken(data.data.token);
                setUserInfo(data.data.userInfo);
                ElMessage.success('Registration successful');
                // Update admin status
                adminStore.updateIsAdmin();
                // Ensure admin status is updated
                if (adminStore.$state.isAdmin) {
                    router.replace('/').then(() => {
                        window.location.reload();
                    }).catch(err => {
                        console.error('Route navigation failed:', err);
                        ElMessage.error('Navigation failed, please refresh the page manually');
                    });
                } else {
                    ElMessage.error('Registration status update failed, please login again');
                }
            } else {
                ElMessage.error(data.message || 'Registration failed');
            }
        });
    });
};
</script>

<style lang="scss" scoped>
.register-container {
    min-height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    background: #f8f9fa;
    padding: 20px;
}

.register-wrapper {
    width: 100%;
    max-width: 420px;
    background: white;
    border-radius: 16px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
    overflow: hidden;
    padding: 40px;
}

.register-header {
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

.register-form-wrapper {
    h2 {
        font-size: 1.8rem;
        color: #2c3e50;
        margin-bottom: 30px;
        text-align: center;
        font-weight: 600;
    }
}

.register-form {
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

    .register-button {
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

.login-hint {
    text-align: center;
    margin-top: 25px;
    color: #6c757d;
    font-size: 0.9rem;

    .login-link {
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
    .register-wrapper {
        padding: 30px 20px;
    }

    .register-header {
        h1 {
            font-size: 2rem;
        }

        p {
            font-size: 1rem;
        }
    }

    .register-form-wrapper {
        h2 {
            font-size: 1.5rem;
        }
    }
}
</style>
