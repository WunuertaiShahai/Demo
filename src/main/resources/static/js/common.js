// common.js - 修复版本
/**
 * 左侧menu用的js文件
 */
function popup() {
    console.log('设置激活导航');
    alert("test操作成功！");
}

// 保存dashboard内容的HTML
let dashboardHTML = '';

// 页面加载完成后初始化
document.addEventListener('DOMContentLoaded', function() {
    console.log('页面加载完成');
    
    // 保存dashboard内容的HTML
    dashboardHTML = document.getElementById('dashboardContent').innerHTML;
    
    // 确保内容区域正确显示dashboard内容
    document.getElementById('contentArea').innerHTML = dashboardHTML;
    
    // 初始化菜单状态
    initializeMenuState();
    
    // 重新绑定统计卡片的点击事件
    bindCardEvents();
});

// 初始化菜单状态
function initializeMenuState() {
    console.log('初始化菜单状态');
    
    // 移除所有激活状态
    const allLinks = document.querySelectorAll('.sidebar-menu .nav-link');
    allLinks.forEach(link => {
        link.classList.remove('active');
    });
    
    // 默认激活首页概览
    const dashboardLink = document.querySelector('.sidebar-menu .nav-link');
    if (dashboardLink && dashboardLink.textContent.includes('首页概览')) {
        dashboardLink.classList.add('active');
    }
    
    // 滚动到页面顶部
    window.scrollTo(0, 0);
}

// 设置激活的导航项
function setActiveNav(activeLink) {
    console.log('设置激活导航');
    
    // 修复选择器：改为 .sidebar-menu .nav-link
    const allLinks = document.querySelectorAll('.sidebar-menu .nav-link');
    allLinks.forEach(link => {
        link.classList.remove('active');
    });
    
    // 为当前点击的链接添加激活状态
    if (activeLink && activeLink.classList) {
        activeLink.classList.add('active');
    }
}

// 重新绑定统计卡片的点击事件
function bindCardEvents() {
    document.querySelectorAll('.stat-card').forEach(card => {
        const text = card.querySelector('p').textContent;
        if (text.includes('总学生数')) {
            card.onclick = showStudentOverview;
        } else if (text.includes('班级数量')) {
            card.onclick = showClassOverview;
        } else if (text.includes('教师人数')) {
            card.onclick = showTeacherOverview;
        } else if (text.includes('课程数量')) {
            card.onclick = showCourseOverview;
        }
    });
}

// 显示学生总览
function showStudentOverview() {
    console.log('显示学生总览');
    window.location.href = '/student/overview';
}

// 显示班级总览
function showClassOverview() {
    console.log('显示班级总览');
    window.location.href = '/class/overview';
}

// 显示教师总览
function showTeacherOverview() {
    console.log('显示教师总览');
    window.location.href = '/teacher/overview';
}

// 显示课程总览
function showCourseOverview() {
    console.log('显示课程总览');
    window.location.href = '/course/overview';
}

// 添加新学生
function addNewStudent() {
    window.location.href = '/student/management/add';
}

// 添加班级和教师管理功能
function addNewClass() {
    window.location.href = '/class/management/add';
}

function manageTeachers() {
    window.location.href = '/teacher/overview';
}

// 成绩录入
function enterGrades() {
    window.location.href = '/grade/management/entry';
}

// 课程安排
function scheduleCourses() {
    window.location.href = '/course/management/schedule';
}

// 数据统计
function viewStatistics() {
    window.location.href = '/statistics/overview'; 
}

function showFeature(event, feature) {
    console.log('显示功能:', feature);
    
    // 设置激活的导航项
    setActiveNav(event.target);
    
    // 滚动到页面顶部
    window.scrollTo(0, 0);
    
    // 根据功能显示不同内容
    let content = '';
    switch(feature) {
        case 'dashboard':
            window.location.href = '/dashboard';
            break;
        case 'student':
            content = '<div class="alert alert-info"><h4>学生信息管理</h4><p>学生信息管理功能开发中...</p></div>';
            break;
        case 'grade':
            content = '<div class="alert alert-info"><h4>成绩管理</h4><p>成绩管理功能开发中...</p></div>';
            break;
        case 'course':
            content = '<div class="alert alert-info"><h4>课程管理</h4><p>课程管理功能开发中...</p></div>';
            break;
        case 'class':
            /*loadClassManagement();*/
            window.location.href = '/class/management/menu';
            return;
        case 'settings':
            content = '<div class="alert alert-info"><h4>系统设置</h4><p>系统设置功能开发中...</p></div>';
            break;
        default:
            content = '<div class="alert alert-warning">功能暂未开放</div>';
    }
    
    document.getElementById('contentArea').innerHTML = content;
}
