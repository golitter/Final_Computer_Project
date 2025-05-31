<template>
    <div class="ai-chat-container" :class="{ 'expanded': isExpanded }">
        <div class="ai-chat-button" @click="toggleChat">
            <el-icon v-if="!isExpanded"><ChatDotRound /></el-icon>
            <el-icon v-else><Close /></el-icon>
        </div>

        <div class="ai-chat-content" v-if="isExpanded">
            <div class="chat-header">
                <h3>AI Assistant</h3>
            </div>

            <div class="chat-messages" ref="messagesContainer">
                <div v-for="(message, index) in messages" :key="index" 
                     :class="['message', message.role]">
                    <div class="message-content">
                        {{ message.content }}
                    </div>
                </div>
                <div v-if="isLoading" class="message assistant">
                    <div class="message-content">
                        <el-icon class="is-loading"><Loading /></el-icon>
                        Thinking...
                    </div>
                </div>
            </div>

            <div class="chat-input">
                <el-input
                    v-model="userInput"
                    placeholder="Type a message..."
                    @keyup.enter="sendMessage"
                    :disabled="isLoading"
                />
                <el-button type="primary" @click="sendMessage" :loading="isLoading">
                    Send
                </el-button>
            </div>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { ref, onMounted, nextTick } from 'vue';
import { ChatDotRound, Close, Loading } from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';
import request from '@/utils/request';

const isExpanded = ref(false);
const userInput = ref('');
const messages = ref<Array<{role: 'user' | 'assistant', content: string}>>([]);
const messagesContainer = ref<HTMLElement | null>(null);
const isLoading = ref(false);

const toggleChat = () => {
    isExpanded.value = !isExpanded.value;
};

const scrollToBottom = async () => {
    await nextTick();
    if (messagesContainer.value) {
        messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight;
    }
};

const sendMessage = async () => {
    if (!userInput.value.trim() || isLoading.value) return;

    const userMessage = userInput.value;
    messages.value.push({ role: 'user', content: userMessage });
    userInput.value = '';
    isLoading.value = true;
    await scrollToBottom();

    try {
        const response = await request({
            url: '/plugins/ai/chat',
            method: 'post',
            data: userMessage,
            headers: {
                'Content-Type': 'application/json'
            },
            timeout: 100000
        });

        if (response.data) {
            const assistantMessage = response.data;
            messages.value.push({ role: 'assistant', content: assistantMessage });
            await scrollToBottom();
        }
    } catch (error) {
        ElMessage.error('Failed to send message, please try again later');
        console.error('Error:', error);
    } finally {
        isLoading.value = false;
    }
};

onMounted(() => {
    messages.value.push({
        role: 'assistant',
        content: 'Hello! I am your AI assistant, how can I help you?'
    });
});
</script>

<style lang="scss" scoped>
.ai-chat-container {
    position: fixed;
    right: 20px;
    top: 80px;
    z-index: 1000;
    transition: all 0.3s ease;

    &.expanded {
        transform: translateX(-50%);
    }
}

.ai-chat-button {
    width: 40px;
    height: 40px;
    background: #3498db;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    transition: all 0.3s ease;

    &:hover {
        background: #2980b9;
        transform: scale(1.1);
    }

    .el-icon {
        color: white;
        font-size: 20px;
    }
}

.ai-chat-content {
    position: absolute;
    right: 0;
    top: 50px;
    width: 300px;
    height: 400px;
    background: white;
    border-radius: 12px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
    overflow: hidden;
    opacity: 0;
    transform: translateY(-20px);
    transition: all 0.3s ease;

    .expanded & {
        opacity: 1;
        transform: translateY(0);
    }
}

.chat-header {
    padding: 15px;
    border-bottom: 1px solid #eee;
    background: #f8f9fa;

    h3 {
        margin: 0 0 10px 0;
        color: #2c3e50;
    }
}

.chat-messages {
    flex: 1;
    padding: 15px;
    overflow-y: auto;
    background: #f8f9fa;

    .message {
        margin-bottom: 15px;
        max-width: 80%;

        &.user {
            margin-left: auto;
            .message-content {
                background: #3498db;
                color: white;
                border-radius: 12px 0 12px 12px;
            }
        }

        &.assistant {
            margin-right: auto;
            .message-content {
                background: white;
                color: #2c3e50;
                border-radius: 0 12px 12px 12px;
            }
        }

        .message-content {
            padding: 10px 15px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
        }

        .is-loading {
            margin-right: 8px;
            animation: rotating 2s linear infinite;
        }
    }
}

.chat-input {
    padding: 15px;
    border-top: 1px solid #eee;
    display: flex;
    gap: 10px;

    .el-input {
        flex: 1;
    }
}

@keyframes rotating {
    from {
        transform: rotate(0deg);
    }
    to {
        transform: rotate(360deg);
    }
}

@media (max-width: 480px) {
    .ai-chat-content {
        width: 280px;
        height: 350px;
    }
}
</style> 