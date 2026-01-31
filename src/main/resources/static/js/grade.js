// grade.js
/**
 * 分数画面使用
 */
document.addEventListener('DOMContentLoaded', function() {
    $('#gradeForm').submit(function(event) {
        event.preventDefault(); // 阻止表单默认提交
        
        $.ajax({
            url: '/grade/management/addGrade',
            type: 'POST',
            data: $(this).serialize(),
            success: function(response) {
                // 检查response是否包含错误消息
                if (response.includes('errorMessage')) {
                    
                    $('#errorMessage').remove();
                    $('.form-container').before(response);
                    
                } else if (response.includes('successMessage')) {
                    // 处理成功消息
                    $('#successMessage').replaceWith(response);
                    
                    // 清空表单（成功时）
                    $('#gradeForm')[0].reset();
                    
                    // 重新设置默认日期
                    const today = new Date().toISOString().split('T')[0];
                    $('input[name="exam_date"]').val(today);
                }
                
                // 可选：重新加载成绩列表
                // loadGradeList();
            },
            error: function() {
                alert('添加失败，请重试！');
            }
        });
    });
});

function validateScoreText(inputElement) {
    const value = inputElement.value;
    const errorElement = document.getElementById('scoreError');
    // 验证是否为0-100的整数
    const isValid = /^(100|[1-9]?[0-9])$/.test(value);
    
    if (isValid) {
        inputElement.classList.remove('is-invalid');
        inputElement.classList.add('is-valid');
        errorElement.style.display = 'none';
    } else {
        inputElement.classList.remove('is-valid');
        inputElement.classList.add('is-invalid');
        errorElement.style.display = 'block';
    }
}