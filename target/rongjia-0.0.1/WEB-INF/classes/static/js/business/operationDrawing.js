/**
 *图纸管理
 */
var pageCurr
$(function () {
    layui.use('table', function(){
        var table = layui.table
        var form = layui.form

        tableQIs = table.render({
            elem: '#operationDrawingList',
            url: '/business/getOperationDrawings',
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
                {field: 'updateTime', title: '更新时间'},
                {field: 'right', title:'操作', width: 200, align: 'center', toolbar: '#optBar'}
            ]],
            done: function (res, curr, count) {
                pageCurr = curr
            }
        })
        table.on('tool(operationDrawingTable)',function(obj){
            var data = obj.data
            if(obj.event === 'del'){
                //删除
                delOperationDrawing(data, data.id)
            }else if(obj.event === 'edit'){
                //编辑
                getOperationDrawing(data, data.id)
            }else if(obj.event === 'recover'){
                //恢复
                recoverOperationDrawing(data, data.id)
            }else if(obj.event === 'view'){
                //浏览
                viewOperationDrawing(data, data.id)
            }else if(obj.event === 'upload'){
                //浏览
                upload()
            }
        })
        //监听提交
        form.on('submit(operationDrawingSubmit)', function(data){
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
            elem: '#operationDrawingFile'
            ,auto: false
            ,accept: 'file' //普通文件
            ,exts: 'pdf' //只允许上传pdf文件
        })
    })
})

function load(obj){
    //重新加载table
    tableQIs.reload({
        where: obj.field,
        page: {
            curr: pageCurr  //从当前页码开始
        }
    })
}

function viewWorkStation(obj, id){

}

function addOperationDrawing(){
    $('#versionItem').attr('style','display:none;')
    openOperationDrawing(null, "添加图纸")
}

function openOperationDrawing(id, title){
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
        content:$('#setOperationDrawing'),
        end:function(){
            cleanOperationDrawing()
        }
    });
}

function cleanOperationDrawing(){
    $("#id").val("")
    $("#invCode").val("")
    $("#procedureCode").val("")
    $("input[name='file']").val("")
    $('#operationDrawingUpload').find('span').remove()
}

function formSubmit(obj){
    var formData = new FormData($('#operationDrawingForm')[0])
    console.log(formData)
    $.ajax({
        type: "POST",
        data: formData,
        url: "/business/setOperationDrawing",
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success: function (data) {
            if(isLogin(data)){
                if (data == "ok") {
                    layer.alert("操作成功",function(){
                        layer.closeAll();
                        cleanOperationDrawing();
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

function getOperationDrawing(obj, id){
    if(obj.del){
        layer.alert("该图纸已经删除，不可进行编辑；</br>  如需编辑，请先<font style='font-weight:bold;' color='blue'>恢复</font>图纸状态。");
    }else{
        //回显数据
        $.get("/business/getOperationDrawing", {"id": id}, function(data){
            if(isLogin(data)){
                if(data.msg == "ok" && data.operationDrawing != null){
                    $('#id').val(data.operationDrawing.id == null ? '': data.operationDrawing.id)
                    $('#invCode').val(data.operationDrawing.invCode == null ? '': data.operationDrawing.invCode)
                    $('#procedureCode').val(data.operationDrawing.procedureCode == null ? '': data.operationDrawing.procedureCode)
                    $('#versionItem').attr("style","display:block;")
                    $('#version').val(data.operationDrawing.version == null ? '': data.operationDrawing.version)
                    openOperationDrawing(id, "设置图纸")
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

function delOperationDrawing(obj, id ){
    if(null != id) {
        layer.confirm('您确定要删除此条图纸吗？', {
            btn: ['确认', '返回']   //按钮
        }, function () {
            $.post('/business/delOperationDrawing', {"id": id}, function (data) {
                if (isLogin(data)) {
                    if (data == "ok") {
                        //回调弹框
                        layer.alert("删除成功!", function () {
                            layer.closeAll()
                            //加载load方法
                            load(obj)   //自定义
                        })
                    } else {
                        layer.alert(data, function () {
                            layer.closeAll()
                            //加载load方法
                            load(obj)   //自定义
                        })
                    }
                }
            })
        }, function () {
            layer.closeAll()
        })
    }
}

function recoverOperationDrawing(obj, id ){
    if(null != id){
        layer.confirm('您确定要恢复此条图纸吗？',{
            btn: ['确认', '返回']   //按钮
        },function(){
            $.post('/business/revoverOperationDrawing',{"id": id}, function(data){
                if(isLogin(data)){
                    if(data == "ok"){
                        //回调弹框
                        layer.alert("恢复成功!", function(){
                            layer.closeAll()
                            //加载load方法
                            load(obj)   //自定义
                        })
                    }else{
                        layer.alert(data, function(){
                            layer.closeAll()
                            //加载load方法
                            load(obj)   //自定义
                        })
                    }
                }
            })
        }, function(){
            layer.closeAll()
        })
    }
}

function viewOperationDrawing(obj, id){
    if(!obj.hasUpload){
        layer.alert("该图纸还未上传pdf文件，如需浏览，请先上传！");
    }else{
        //回显数据
        $.get("/business/getOperationDrawingPDF", {"id": id}, function(data){
            if(isLogin(data)){
                if(data.msg == "ok" && data.fileName != null){

                    viewOperationDrawingPDF(data.fileName,"浏览图纸")
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

function viewOperationDrawingPDF(path, title){
    PDFObject.embed("/business/getPDF?filePath=" + path, "#example1")
    layer.open({
        type:1,
        title: title,
        offset: 't',
        fixed:false,
        resize :false,
        shadeClose: true,
        area: ['800px','1000px'],
        content:$('#viewOperationDrawing'),
        end:function(){
            cleanViewOperationDrawing()
        }
    });

}

function cleanViewOperationDrawing(){

}