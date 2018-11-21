/**
 *作业指导书管理
 */
var pageCurr
$(function () {
    layui.use('table', function(){
        var table = layui.table
        var form = layui.form

        tableOIs = table.render({
            elem: '#operationInstructionList',
            url: '/business/getOperationInstructions',
            method: 'post',
            cellMinWidth: 80,
            page: true,
            request: {
                pageName: 'page',
                limitName: 'limit'
            },
            response: {
                statusName: 'code',
                statusCode: 200,
                countName: 'totals',
                dataName: 'list'
            },
            cols: [[
                {type: 'numbers'},
                {field: 'id', title: 'ID', width:80, unresize: true, sort:true},
                {field: 'invCode', title: '产品编号'},
                {field: 'invName', title: '产品名称'},
                {field: 'invStd', title: '规格型号'},
                {field: 'procedureCode', title: '工序号'},
                {field: 'procedureName', title: '工序名'},
                {field: 'version', title: '版本号'},
                {field: 'hasUpload', title: '是否上传', templet: '#uploadcheck'},
                {field: 'right', title:'操作', width: 200, align: 'center', toolbar: '#optBar'}
            ]],
            done: function (res, curr, count) {
                pageCurr = curr
            }
        })
        table.on('tool(operationInstructionTable)',function(obj){
            var data = obj.data
            if(obj.event === 'del'){
                //删除
                delOperationInstruction(data, data.id, data.workStationCode)
            }else if(obj.event === 'edit'){
                //编辑
                getOperationInstruction(data, data.id)
            }else if(obj.event === 'recover'){
                //恢复
                recoverOperationInstruction(data, data.id, data.workStationCode)
            }else if(obj.event === 'view'){
                //浏览
                viewOperationInstruction(data, data.id)
            }else if(obj.event === 'upload'){
                //浏览
                upload()
            }
        })
        //监听提交
        form.on('submit(operationInstructionSubmit)', function(data){
            // TODO 校验
            formSubmit(data)
            return false
        })
    })
    //搜索框
    layui.use(['form'], function(){
        var form = layui.form , layer = layui.layer
        //TODO 数据校验
        //监听搜索框
        form.on('submit(searchSubmit)', function(data){
            //重新加载table
            load(data)
            return false
        })
    })

    layui.use('upload', function () {
        upload = layui.upload
        var uploadInst = upload.render({
            elem: '#oiFile'
            ,auto: false
            ,accept: 'file' //普通文件
            ,exts: 'pdf' //只允许上传pdf文件
        })
    })
})

function load(obj){
    //重新加载table
    tableOIs.reload({
        where: obj.field,
        page: {
            curr: pageCurr  //从当前页码开始
        }
    })
}

function addOperationInstruction(){
    $('#versionItem').attr('style','display:none;')
    openOperationInstruction(null, "添加作业指导书")
}

function openOperationInstruction(id, title){
    if(id == null || id == ""){
        $("#id").val("");
    }
    layer.open({
        type:1,
        title: title,
        fixed:false,
        resize :false,
        shadeClose: true,
        area: ['550px'],
        content:$('#setOperationInstruction'),
        end:function(){
            cleanOperationInstruction()
        }
    });
}

function cleanOperationInstruction(){
    $("#id").val("")
    $("#invCode").val("")
    $("#procedureCode").val("")
    $("input[name='file']").val("")
    $('#oiUpload').find('span').remove()
}

function formSubmit(obj){
    var formData = new FormData($('#operationInstructionForm')[0])
    console.log(formData)
    $.ajax({
        type: "POST",
        data: formData,
        url: "/business/setOperationInstruction",
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success: function (data) {
            if(isLogin(data)){
                if (data == "ok") {
                    layer.alert("操作成功",function(){
                        layer.closeAll();
                        cleanOperationInstruction();
                        //$("#id").val("");
                        //加载页面
                        load(obj);
                    });
                } else {
                    layer.alert(data,function(){
                        layer.closeAll();
                        //加载load方法
                        load(obj);//自定义
                    });
                }
            }
        },
        error: function () {
            layer.alert("操作请求错误，请您稍后再试",function(){
                layer.closeAll();
                //加载load方法
                load(obj);//自定义
            });
        }
    });
}

function getOperationInstruction(obj, id){
    if(obj.del){
        layer.alert("该作业指导书已经删除，不可进行编辑；</br>  如需编辑，请先<font style='font-weight:bold;' color='blue'>恢复</font>作业指导书状态。");
    }else{
        //回显数据
        $.get("/business/getOperationInstruction", {"id": id}, function(data){
            if(isLogin(data)){
                if(data.msg == "ok" && data.operationInstruction != null){
                    $('#id').val(data.operationInstruction.id == null ? '': data.operationInstruction.id)
                    $('#invCode').val(data.operationInstruction.invCode == null ? '': data.operationInstruction.invCode)
                    $('#procedureCode').val(data.operationInstruction.procedureCode == null ? '': data.operationInstruction.procedureCode)
                    $('#versionItem').attr("style","display:block;")
                    $('#version').val(data.operationInstruction.version == null ? '': data.operationInstruction.version)
                    openOperationInstruction(id, "设置作业指导书")
                }else{
                    //弹出错误提示
                    layer.alert(data.msg,function () {
                        layer.closeAll();
                    });
                }
            }
        })
    }
}

function viewOperationInstruction(obj, id){
    if(!obj.hasUpload){
        layer.alert("该作业指导书还未上传pdf文件，如需浏览，请先上传！");
    }else{
        //回显数据
        $.get("/business/getOperationInstructionPDF", {"id": id}, function(data){
            if(isLogin(data)){
                if(data.msg == "ok" && data.fileName != null){

                    viewOperationInstructionPDF(data.fileName,"浏览作业指导书")
                }else{
                    //弹出错误提示
                    layer.alert(data.msg,function () {
                        layer.closeAll();
                    });
                }
            }
        })
    }
}

function viewOperationInstructionPDF(path, title){
    PDFObject.embed("/business/getPDF?filePath=" + path, "#example1")
    layer.open({
        type:1,
        title: title,
        offset: 't',
        fixed:false,
        resize :false,
        shadeClose: true,
        area: ['800px','1000px'],
        content:$('#viewOperationInstruction'),
        end:function(){
            cleanViewOperationInstruction()
        }
    });

}

function cleanViewOperationInstruction(){

}