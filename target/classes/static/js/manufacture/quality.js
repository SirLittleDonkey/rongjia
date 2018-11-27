/**
 *工位看板管理
 */
var pageCurr;
var workStationCode;
$(function(){

    $.post("/manufacture/getWorkStationCode", function(data){
        workStationCode = data
        $("#workStationCode").html("工位编号：" + workStationCode)
    })

    layui.use('table', function(){
        var table = layui.table
        var form = layui.form
        tableIns = table.render({
            elem: '#dailyWorkPlanList'
            ,url:'/manufacture/getDailyWorkPlanQuality?workStationCode=' + workStationCode
            ,method: 'post' //默认：get请求
            ,cellMinWidth: 80,
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
                ,{field:'prodPlanId', title:'ProdPlanId',width:80, unresize: true}
                ,{field:'planDate', title:'日期'}
                ,{field:'invCode', title:'产品编号'}
                ,{field:'invName', title: '产品名称'}
                ,{field:'invStd', title: '规格型号'}
                ,{field:'procedureName', title: '工序'}
                ,{field:'planQty', title:'计划数量'}
                ,{field:'realQty', title:'实际数量'}
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
        table.on('tool(dailyWorkPlanTable)',function(obj){
            var data = obj.data
            if(obj.event === 'firstInspect'){
                //首检
                startFirstInspect(data, data.prodPlanId)
            }
            if(obj.event === 'endInspect'){
                //终检
                startEndInspect(data, data.prodPlanId)
            }
        })
    })
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

function startFirstInspect(obj, prodPlanId){
    //回显数据
    $.get("/manufacture/getFirstInspectData", {"prodPlanId": prodPlanId}, function(data){
        if(isLogin(data)){
            if(data.msg == "ok" && data.firstInspectVO != null){
                $('#prodPlanId').val(data.firstInspectVO.prodPlanId == null ? '': data.firstInspectVO.prodPlanId)
                $('#invCode').html(data.firstInspectVO.invCode == null ? '': data.firstInspectVO.invCode)
                $('#invName').html(data.firstInspectVO.invName == null ? '': data.firstInspectVO.invName)
                $('#invStd').html(data.firstInspectVO.invStd == null ? '': data.firstInspectVO.invStd)
                PDFObject.embed("/business/getPDF?filePath=" + data.firstInspectVO.filePath, "#example1")
                layui.use('element', function() {
                    var $ = layui.jquery
                        , element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
                    element.tabChange('demo', 'startWork'); //切换到：用户管理
                })
            }else{
                //弹出错误提示
                layer.alert(data.msg,function () {
                    layer.closeAll();
                });
            }
        }
    })

}

function firstInspectSubmit(){
    layer.alert('确定首检吗？',{
        closeBtn: 1,
        btn: ['确定','取消'],
        yes: function() {
            $.post("/manufacture/firstInspect",{"prodPlanId": $("#prodPlanId").val()}, function (data){
                if(isLogin(data)){
                    if(data.msg == "ok" ){
                        layer.alert('首检成功');
                        setTimeout(function(){
                            $(window).attr('location','/manufacture/quality')
                        }, 3000)

                    }else{
                        //弹出错误提示
                        layer.alert(data.msg,function () {
                            layer.closeAll();
                        });
                    }
                }
            })
        }
    })

}

function startEndInspect(obj, prodPlanId){
    if(!obj.hasInspected){
        layer.alert("请先首检后再终检")
    }else{
    //回显数据
    $.get("/manufacture/getEndInspectData", {"prodPlanId": prodPlanId}, function(data){
        if(isLogin(data)){
            if(data.msg == "ok" && data.endInspectVO != null){
                $('#prodPlanId').val(data.endInspectVO.prodPlanId == null ? '': data.endInspectVO.prodPlanId)
                $('#einvCode').html(data.endInspectVO.invCode == null ? '': data.endInspectVO.invCode)
                $('#einvName').html(data.endInspectVO.invName == null ? '': data.endInspectVO.invName)
                $('#einvStd').html(data.endInspectVO.invStd == null ? '': data.endInspectVO.invStd)
                PDFObject.embed("/business/getPDF?filePath=" + data.endInspectVO.filePath, "#eexample1")
                layui.use('element', function() {
                    var $ = layui.jquery
                        , element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
                    element.tabChange('demo', 'endCheck'); //切换到：用户管理
                })
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

function endInspectSubmit(){
    layer.alert('确定终检吗？',{
        closeBtn: 1,
        btn: ['确定','取消'],
        yes: function() {
            $.post("/manufacture/endInspect",{"prodPlanId": $("#prodPlanId").val()}, function (data){
                if(isLogin(data)){
                    if(data.msg == "ok" ){
                        layer.alert('终检成功')
                        setTimeout(function(){
                            $(window).attr('location','/manufacture/quality')
                        }, 3000);

                    }else{
                        //弹出错误提示
                        layer.alert(data.msg,function () {
                            layer.closeAll();
                        });
                    }
                }
            })
        }
    })

}






