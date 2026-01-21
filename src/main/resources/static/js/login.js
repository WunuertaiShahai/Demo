// login.js - 修复版本
/**
 * 登录页面输入框验证逻辑
 * 修复表单提交问题
 */

document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');
    const usernameInput = document.getElementById('usernameInput');
    const passwordInput = document.getElementById('passwordInput');
    const usernameError = document.getElementById('usernameError');
    const passwordError = document.getElementById('passwordError');
    
    let usernameTouched = false;
    let passwordTouched = false;
    
    // 初始化页面
    function initializePage() {
        usernameInput.classList.remove('error', 'valid');
        passwordInput.classList.remove('error', 'valid');
        usernameError.classList.remove('show');
        passwordError.classList.remove('show');
        
        // 如果有内容，设置为valid状态
        if (usernameInput.value.trim() !== '') {
            usernameInput.classList.add('valid');
        }
        if (passwordInput.value.trim() !== '') {
            passwordInput.classList.add('valid');
        }
    }
    
    // 验证输入框
    function validateInput(input, errorElement, touched) {
        const value = input.value.trim();
        
        if (value === '') {
            if (touched) {
                input.classList.add('error');
                input.classList.remove('valid');
                errorElement.classList.add('show');
            }
            return false;
        } else {
            input.classList.remove('error');
            input.classList.add('valid');
            errorElement.classList.remove('show');
            return true;
        }
    }
    
    // 用户名输入框事件
    usernameInput.addEventListener('focus', function() {
        usernameTouched = true;
    });
    
    usernameInput.addEventListener('blur', function() {
        validateInput(usernameInput, usernameError, usernameTouched);
    });
    
    usernameInput.addEventListener('input', function() {
        validateInput(usernameInput, usernameError, true);
    });
    
    // 密码输入框事件
    passwordInput.addEventListener('focus', function() {
        passwordTouched = true;
    });
    
    passwordInput.addEventListener('blur', function() {
        validateInput(passwordInput, passwordError, passwordTouched);
    });
    
    passwordInput.addEventListener('input', function() {
        validateInput(passwordInput, passwordError, true);
    });
    
    // 表单提交事件 - 修复版本
    loginForm.addEventListener('submit', function(event) {
        // 设置所有输入框为已触摸状态
        usernameTouched = true;
        passwordTouched = true;
        
        // 验证所有输入框
        const isUsernameValid = validateInput(usernameInput, usernameError, true);
        const isPasswordValid = validateInput(passwordInput, passwordError, true);
        
        // 如果有验证失败，阻止表单提交
        if (!isUsernameValid || !isPasswordValid) {
            event.preventDefault();
            return false;
        }
        
        // 验证通过，允许表单提交到Spring Security
        return true;
    });
    
    // 页面加载完成后初始化
    initializePage();
});