// student.js
/**
 * 学生画面使用
 */
document.addEventListener('DOMContentLoaded', function() {
    const collegeSelect = document.getElementById('college');
    const majorSelect = document.getElementById('major');
    // 监听学院下拉框的变化
    collegeSelect.addEventListener('change', function() {

        $('#class').html('<option value="">请先选择专业</option>');

        const selectedCollegeId = this.value;
        // 只有当选择的学院ID有效（非0或空）时才发起请求
        if (selectedCollegeId && selectedCollegeId !== '0') {
            // 发起AJAX请求
            $.ajax({
                url: '/student/college/majors', // 对应后端接口的URL
                type: 'GET',
                data: {
                    collegeId: selectedCollegeId // 传递参数
                },
                success: function(data) {
                    // 成功回调，data是后端返回的HTML片段
                    // 用返回的片段更新专业下拉框
                    $('#major').html(data);
                    
                    for (var i = 0; i < majorSelect.options.length; i++) {
                      if (majorSelect.options[i].value === '0') {
                        // 3. 修改找到的option的值和显示文本
                        majorSelect.options[i].text = '请选择专业';
                        break; // 找到后退出循环
                      }
                    }
                    
                },
                error: function() {
                    alert('获取专业数据失败！');
                }
            });
        } else {
            // 如果学院选择重置，则清空专业下拉框
            $('#major').html('<option value="">请先选择学院</option>');
        }
    });
});

document.addEventListener('DOMContentLoaded', function() {

    const majorSelect = document.getElementById('major');
    // 监听专业下拉框的变化
    majorSelect.addEventListener('change', function() {
        selectChanged();
    });
    

    const gradeSelect = document.getElementById("grade_year");
    // 监听年级下拉框的变化
    gradeSelect.addEventListener('change', function() {
        selectChanged();
    });
});

function selectChanged() {

    const majorSelect = document.getElementById('major');
    const classSelect = document.getElementById('class');

    // 获取年级的值
    const gradeSelect = document.getElementById("grade_year");
    const gradeVal = gradeSelect.value;
    // 获取学院的值
    const collegeSelect = document.getElementById('college');
    const collegeVal = collegeSelect.value;

    const selectedMajorId = majorSelect.value;
    // 只有当选择的专业ID有效（非0或空）时才发起请求
    if (selectedMajorId && selectedMajorId !== '0') {
        // 发起AJAX请求
        $.ajax({
            url: '/student/college/classes', // 对应后端接口的URL
            type: 'GET',
            data: {
                collegeId: collegeVal, // 传递参数:学院ID
                majorId: selectedMajorId, // 传递参数:专业ID
                grade: gradeVal // 传递参数:年级
            },
            success: function(data) {
                // 成功回调，data是后端返回的HTML片段
                // 用返回的片段更新专业下拉框
                $('#class').html(data);
                
                for (var i = 0; i < classSelect.options.length; i++) {
                  if (classSelect.options[i].value === '0') {
                    // 3. 修改找到的option的值和显示文本
                    classSelect.options[i].text = '请选择班级';
                    break; // 找到后退出循环
                  }
                }
                
            },
            error: function() {
                alert('获取班级数据失败！');
            }
        });
    } else {
        // 如果专业选择重置，则清空班级下拉框
        $('#class').html('<option value="">请先选择专业</option>');
    }
}


document.addEventListener('DOMContentLoaded', function() {

    const gradeSelect = document.getElementById("grade_year");
    // 监听年级下拉框的变化
    gradeSelect.addEventListener('change', function() {
        // 如果专业选择重置，则清空学号输入框
        document.getElementById('student_id').value = '';
    });

    const collegeSelect = document.getElementById('college');
    // 监听年级下拉框的变化
    collegeSelect.addEventListener('change', function() {
        // 如果专业选择重置，则清空学号输入框
        document.getElementById('student_id').value = '';
    });

    const majorSelect = document.getElementById('major');
    // 监听年级下拉框的变化
    majorSelect.addEventListener('change', function() {
        // 如果专业选择重置，则清空学号输入框
        document.getElementById('student_id').value = '';
    });

    const classSelect = document.getElementById('class');
    // 监听班级下拉框的变化
    classSelect.addEventListener('change', function() {
        studentId();
    });
});


function studentId() {

    const classSelect = document.getElementById('class');
    const selectedClassId = classSelect.value;
    // 只有当选择的班级ID有效（非0或空）时才发起请求
    if (selectedClassId && selectedClassId !== '0') {
        // 发起AJAX请求
        $.ajax({
            url: '/student/college/maxStudentId', // 对应后端接口的URL
            type: 'GET',
            data: {
                classId: selectedClassId // 传递参数
            },
            success: function(data) {
                // 成功回调，data是后端返回的HTML片段
                // 用返回的片段更新专业下拉框
                /*$('#student_id').html(data);*/
                $('#student_id').val(data);
            },
            error: function() {
                alert('获取学号数据失败！');
            }
        });
    } else {
        // 如果专业选择重置，则清空学号输入框
        document.getElementById('student_id').value = '';
    }
}



document.addEventListener('DOMContentLoaded', function() {
    $('#studentForm').submit(function(event) {
        event.preventDefault(); // 阻止表单默认提交（即页面跳转）
        
        $.ajax({
            url: '/student/management/add',
            type: 'POST',
            data: $(this).serialize(),
            success: function(response) {

                const studentId = document.getElementById('student_id');
                const tmp = Number(studentId.value);
                $('input[id="student_id"]').val(tmp + 1);
                $('input[id="name"]').val('');
                $('input[type="date"]').val('');
                $('select[name="gender"]').val('0');
                $('input[type="tel"]').val('');
                $('input[type="email"]').val('');
                $('textarea[name="address"]').val('');
            },
            error: function() {
                alert('添加失败，请重试！');
            }
        });
    });
});