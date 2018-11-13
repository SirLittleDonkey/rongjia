/**
 *工序管理
 */
var pageCurr;
$(function(){
    layui.use('table', function(){
        var table = layui.table
        var form = layui.form

        tableIns = table.render({
            elem: '#procedureList'
            ,url:'/basic/getProcedures'
            ,method: 'post' //默认：get请求
            ,cellMinWidth: 80
            ,page: true,
            request: {
                pageName: 'page' //页码的参数名称，默认：page
                ,limitName: 'limit' //每页数据量的参数名，默认：limit
            },response:{
                statusName: 'code' //数据状态的字段名称，默认：code
                ,statusCode: 200 //成功的状态码，默认：0
                ,countName: 'totals' //数据总数的字段名称，默认：count
                ,dataName: 'list' //数据列表的字段名称，默认：data
            }
            ,cols: [[
                {type:'numbers'}
                ,{field:'id', title:'ID',width:80, unresize: true, sort: true}
                ,{field:'procedureCode', title:'工序号'}
                ,{field:'procedureName', title:'工序名'}
                ,{fixed:'right', title:'操作',width:140,align:'center', toolbar:'#optBar'}
            ]]
            ,  done: function(res, curr, count){
                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                //console.log(res);
                //得到当前页码
                //console.log(curr);
                //得到数据总量
                //console.log(count);
                pageCurr=curr;
            }
        })
        table.on('tool(procedureTable)',function(obj){
            var data = obj.data
            if(obj.event === 'del'){
                //删除
                delProcedure(data, data.id, data.procedureCode)
            }else if(obj.event === 'edit'){
                //编辑
                getProcedure(data, data.id)
            }else if(obj.event === 'recover'){
                //恢复
                recoverProcedure(data, data.id, data.procedureCode)
            }
        })
        //监听提交
        form.on('submit(procedureSubmit)', function(data){
            // TODO 校验
            formSubmit(data);
            return false;
        })

    })
    //搜索框
    layui.use(['form'], function(){
        var form = layui.form ,layer = layui.layer
        //TODO 数据校验
        //监听搜索框
        form.on('submit(searchSubmit)', function(data){
            //重新加载table
            load(data);
            return false;
        });
    });
})



function load(obj){
    //重新加载table
    tableIns.reload({
        where: obj.field
        , page: {
            curr: pageCurr //从当前页码开始
        }
    });
}

//添加工位
function addProcedure(){
    openProcedure(null,"开通用户");
}

function delProcedure(obj, id, procedureCode){
    if(null != id){
        layer.confirm('您确定要删除' + procedureCode + '工位吗？',{
            btn: ['确认', '返回']   //按钮
        },function(){
            $.post('/basic/delProcedure',{"id": id}, function(data){
                if(isLogin(data)){
                    if(data == "ok"){
                        //回调弹框
                        layer.alert("删除成功!", function(){
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

function recoverProcedure(obj, id ,procedureCode){
    if(null != id){
        layer.confirm('您确定要恢复' + procedureCode + '工序吗？',{
            btn: ['确认', '返回']   //按钮
        },function(){
            $.post('/basic/revoverProcedure',{"id": id}, function(data){
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

function getProcedure(obj, id){
    if(obj.del){
        layer.alert("该工位已经删除，不可进行编辑；</br>  如需编辑，请先<font style='font-weight:bold;' color='blue'>恢复</font>工位状态。");
    }else{
        //回显数据
        $.get("/basic/getProcedure", {"id": id}, function(data){
            if(isLogin(data)){
                if(data.msg == "ok" && data.procedure != null){
                    $('#id').val(data.procedure.id == null ? '': data.procedure.id)
                    $('#procedureCode').val(data.procedure.procedureCode == null ? '': data.procedure.procedureCode)
                    $('#procedureName').val(data.procedure.procedureName == null ? '': data.procedure.procedureName)
                    openProcedure(id, "设置工位")
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

function openProcedure(id, title){
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
        content:$('#setProcedure'),
        end:function(){
            cleanProcedure();
        }
    });
}

function cleanProcedure(){
    $('#procedureCode').val('')
    $('#procedureName').val('')
}

function formSubmit(obj){
    $.ajax({
        type: "POST",
        data: $("#procedureForm").serialize(),
        url: "/basic/setProcedure",
        success: function (data) {
            if(isLogin(data)){
                if (data == "ok") {
                    layer.alert("操作成功",function(){
                        layer.closeAll();
                        cleanProcedure();
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