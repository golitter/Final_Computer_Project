from wordcloud import WordCloud
import matplotlib.pyplot as plt

# 定义关键词和对应的重要程度
keywords = {
    "文章创建": 5, "文章编辑": 5, "文章发布": 5, "文章删除": 5, "全生命周期管理": 4,
    "结构合理性": 4, "数据一致性": 5, "数据完整性": 5,
    "响应速度": 5, "并发处理": 4, "加载时间": 3, "缓存策略": 3,
    "用户认证": 5, "授权控制": 4, "数据加密": 4, "防止未授权访问": 5, "防止数据泄露": 5,
    "友好性": 4, "直观操作": 3, "用户体验": 5, "前端框架": 4,
    "现代前端框架": 3, "后端技术": 3, "实际应用": 2,
    "代码可读性": 3, "模块化设计": 4, "文档完善": 3, "开发工具": 2,
    "可扩展性": 4, "可维护性": 4, "测试覆盖": 3, "部署策略": 2
}
keywords = {
    "Security ":5,

    "Article Creation": 5, "Article Editing": 5, "Article Publishing": 5, "Article Deletion": 5, "Full Lifecycle Management": 4,
    "Structural Rationality": 4,"Data Consistency": 5, "Data Integrity": 5,
    "Response Speed": 5, "Concurrency Handling": 4, "Loading Time": 3, "Caching Strategy": 3,
    "User Authentication": 5, "Authorization Control": 4, "Data Encryption": 4, "Prevent Unauthorized Access": 5, "Prevent Data Leakage": 5,
    "User Friendliness": 4, "Intuitive Operation": 3, "User Experience": 5, "Frontend Framework": 4,
    "Modern Frontend Framework": 3, "Backend Technology": 3, "Practical Application": 2,
    "Code Readability": 3, "Modular Design": 4, "Documentation Completeness": 3, "Development Tools": 2,
    "Scalability": 4, "Maintainability": 4, "Test Coverage": 3, "Deployment Strategy": 2
}

# 生成词云文本
wordcloud_text = " ".join([word * freq for word, freq in keywords.items()])

# 创建词云对象
wordcloud = WordCloud(width=800, height=400, background_color='white').generate(wordcloud_text)

# 显示词云
plt.figure(figsize=(10, 5))
plt.imshow(wordcloud, interpolation='bilinear')
plt.axis('off')
plt.show()
