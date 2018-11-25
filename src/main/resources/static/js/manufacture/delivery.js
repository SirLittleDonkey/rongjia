/**
 *发货执行看板
 */

var pageCurr


$(function(){
    setInterval(function(){
        layui.use('table', function(){
            var table = layui.table
            table.render({
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
                    {field:'cusCode', title:'客户编码'}
                    ,{field:'soCode', title:'订单号'}
                    ,{field:'invCode', title:'产品编号'}
                    ,{field:'invName', title: '产品名称'}
                    ,{field:'preDate', title: '要求发货时间'}
                    ,{field:'planQty', title:'计划数'}
                    ,{field:'delyQty', title:'发货数'}
                    ,{field:'completionRate', title:'完成率'}
                    ,{field:'state', title:'状态'}
                ]],
                done: function(res, curr, count){
                    pageCurr = curr + 1
                }

            })
        })
    },30000)
})
