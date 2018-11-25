/**
 *计划与进程看板
 */
var pageCurr

$(function(){
    layui.use('table', function(){
        var table = layui.table
        table.render({
            elem: '#planProcessList',
            url: 'getPlanProcessList',
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
                {field:'cusName', title:'客户名称'}
                ,{field:'workShopName', title:'车间'}
                ,{field:'workStationCode', title:'工位号'}
                ,{field:'invCode', title: '产品编码'}
                ,{field:'invName', title: '产品名称'}
                ,{field:'planQty', title:'计划数'}
                ,{field:'qualifiedQty', title:'实际合格'}
                ,{field:'completionRate', title:'完成率'}
                ,{field:'state', title:'状态'}
            ]],
            done: function(res, curr, count){
                pageCurr = curr
            }
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