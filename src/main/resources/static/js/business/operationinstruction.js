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
                {field: 'hasUpload', title: '是否上传', templet: '#uploadcheck'},
                {field: 'right', title:'操作', width: 140, align: 'center', toolbar: '#optBar'}
            ]],
            done: function (res, curr, count) {
                pageCurr = curr
            }
        })
        table.on('tool(operationInstructionTable)',function(obj){
            var data = obj.data
            if(obj.event === 'del'){
                //删除
                delWorkStation(data, data.id, data.workStationCode)
            }else if(obj.event === 'edit'){
                //编辑
                getWorkStation(data, data.id)
            }else if(obj.event === 'recover'){
                //恢复
                recoverWorkStation(data, data.id, data.workStationCode)
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
        var form = layui.form ,layer = layui.layer
        //TODO 数据校验
        //监听搜索框
        form.on('submit(searchSubmit)', function(data){
            //重新加载table
            load(data)
            return false
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
