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
                ,{field:'id', title:'ID',width:80, unresize: true}
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
            if(obj.event === 'start'){
                //开工

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


