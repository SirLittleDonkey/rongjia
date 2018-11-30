/**
 *发货执行看板
 */

var pageCurr


$(function(){
    kaishi()
    setInterval(function(){
        $('#nowTime').html("当前时间：" + getNowDate())
    },1000)
    layui.use('table', function(){
        var table = layui.table
        tableIns =table.render({
            elem: '#dailyDeliveryList',
            url: 'getDailyDeliveryList',
            method: 'post',
            page: {
                curr:   pageCurr
            },
            request: {
                pageName: 'page',
                limitName: 'limit'
            },
            response:{
                statusName: 'code' //数据状态的字段名称，默认：code
                ,statusCode: 200 //成功的状态码，默认：0
                ,countName: 'totals' //数据总数的字段名称，默认：count
                ,dataName: 'list' //数据列表的字段名称，默认：data
            },
            cols: [[
                {field:'cusName', title:'客户名称',style:'font-size:25px;'}
                ,{field:'soCode', title:'订单号',style:'font-size:25px;'}
                ,{field:'invCode', title:'产品编号',style:'font-size:25px;'}
                ,{field:'invName', title: '产品名称',style:'font-size:25px;'}
                ,{field:'preDate', title: '要求发货时间',style:'font-size:25px;'}
                ,{field:'planQty', title:'计划数',style:'font-size:25px;'}
                ,{field:'delyQty', title:'发货数',style:'font-size:25px;'}
                ,{field:'completionRate', title:'完成率',style:'font-size:25px;'}
                ,{field:'state', title:'状态',style:'font-size:25px;'}
            ]],
            done: function(res, curr, count){
                if(curr == Math.ceil(count/10)){
                    pageCurr = 1
                }else{
                    pageCurr = curr + 1
                }
                $('tr:eq(0)').css({'background-color': '#191970', 'color': 'white','font-size':'25px'});
                $('th').css({'font-size':'25px'});
                LayUIDataTable.SetJqueryObj($);
                var currentRowDataList = LayUIDataTable.ParseDataTable(function (index, currentData, rowData) {
                    console.log("当前页数据条数:" + currentRowDataList.length)
                    console.log("当前行索引：" + index);
                    console.log("触发的当前行单元格：" + currentData);
                    console.log("当前行数据：" + JSON.stringify(rowData));

                    var msg = '<div style="text-align: left"> 【当前页数据条数】' + currentRowDataList.length + '<br/>【当前行索引】' + index + '<br/>【触发的当前行单元格】' + currentData + '<br/>【当前行数据】' + JSON.stringify(rowData) + '</div>';
                    layer.msg(msg)
                })
                $.each(currentRowDataList, function (index, obj) {
                    if ('紧急'=== obj.state.value){
                        obj.state.row.css({'background-color': 'yellow', 'color': 'white'});
                    } else if ('超期'=== obj.state.value){
                        obj.state.row.css({'background-color': 'red', 'color': 'white'});
                    } else {
                        obj.state.row.css({'background-color': '#191970', 'color': 'white'});
                    }
                })
            }

        })
    })
    layui.use(['form'], function(){
        var form = layui.form ,layer = layui.layer
        //TODO 数据校验
        //监听搜索框
        form.on('submit(searchSubmit)', function(data){
            //重新加载table
            kaishi()
            pageCurr = 1;
            load(data);
            return false;
        });
    });
})

function load(obj){
    $("#dailyDeliverySearch").css("display","none")
    //重新加载table
    tableIns.reload({
        where: obj.field
        , page: {
            curr: pageCurr //从当前页码开始
        }
    });
    setInterval(function(){
        tableIns.reload({
            where: obj.field
            , page: {
                curr: pageCurr //从当前页码开始
            }
        });
    },10000)

}
function getNowDate() {
    var date = new Date();
    var sign1 = "-";
    var sign2 = ":";
    var year = date.getFullYear() // 年
    var month = date.getMonth() + 1; // 月
    var day  = date.getDate(); // 日
    var hour = date.getHours(); // 时
    var minutes = date.getMinutes(); // 分
    var seconds = date.getSeconds() //秒
    // 给一位数数据前面加 “0”
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (day >= 0 && day <= 9) {
        day = "0" + day;
    }
    if (hour >= 0 && hour <= 9) {
        hour = "0" + hour;
    }
    if (minutes >= 0 && minutes <= 9) {
        minutes = "0" + minutes;
    }
    if (seconds >= 0 && seconds <= 9) {
        seconds = "0" + seconds;
    }
    var currentdate = year + sign1 + month + sign1 + day + " " + hour + sign2 + minutes + sign2 + seconds
    return currentdate;
}
