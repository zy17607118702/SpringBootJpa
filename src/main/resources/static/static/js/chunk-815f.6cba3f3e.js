(window.webpackJsonp=window.webpackJsonp||[]).push([["chunk-815f"],{"/zEO":function(e,t,i){},"3XEt":function(e,t,i){},"6bP5":function(e,t,i){"use strict";var a=i("Qq1u");i.n(a).a},DIoh:function(e,t,i){"use strict";var a=i("3XEt");i.n(a).a},GdYA:function(e,t,i){},"L/IT":function(e,t,i){},OpvW:function(e,t,i){"use strict";var a=i("/zEO");i.n(a).a},Qq1u:function(e,t,i){},bR9e:function(e,t,i){"use strict";var a=i("L/IT");i.n(a).a},cOtO:function(e,t,i){"use strict";i.r(t);i("wMd2");var a=i("gDS+"),n=i.n(a),s=i("t3Un"),o={getList:function(e){return Object(s.a)({url:"/role/list",method:"post",data:n()(e)})},getDetailById:function(e){return Object(s.a)({url:"/role/userlist/"+e,method:"get"})},getResourceDetailById:function(e){return Object(s.a)({url:"/role/resourcelist/"+e,method:"get"})},createData:function(e){return Object(s.a)({url:"/mdm/certificate/createCertificate",method:"post",data:n()(e)})},updateData:function(e){return Object(s.a)({url:"/mdm/certificate/updateCertificate",method:"post",data:n()(e)})},delresource:function(e){return Object(s.a)({url:"/role/delresources",method:"post",data:n()(e)})},getTemplateTypeOptions:function(){return Object(s.a)({url:"/mdm/certificate/getTemplateCombo",method:"get"})},getExpandRowData:function(e){return Object(s.a)({url:"/mdm/storage/getDate",method:"get",params:e})},getunselectresourceById:function(e){return Object(s.a)({url:"/role/unselectresource/"+e,method:"get"})},getunselectactresourceById:function(e){return Object(s.a)({url:"/role/unselectactresource/"+e,method:"get"})},eaddresources:function(e){return Object(s.a)({url:"/role/addresources",method:"post",data:n()(e)})},deniedactionsources:function(e){return Object(s.a)({url:"/role/deniedaction",method:"post",data:n()(e)})}},r=i("WAwK"),l={name:"multipane",props:{layout:{type:String,default:"vertical"}},data:function(){return{isResizing:!1}},computed:{classnames:function(){return["multipane","layout-"+this.layout.slice(0,1),this.isResizing?"is-resizing":""]},cursor:function(){return this.isResizing?"vertical"==this.layout?"col-resize":"row-resize":""},userSelect:function(){return this.isResizing?"none":""}},methods:{onMouseDown:function(e){var t=e.target,i=e.pageX,a=e.pageY;if(t.className&&t.className.match("multipane-resizer")){var n=this,s=n.$el,o=n.layout,r=t.previousElementSibling,l=r.offsetWidth,c=r.offsetHeight,u=!!(r.style.width+"").match("%"),d=window.addEventListener,p=window.removeEventListener,h=function(e,t){if(void 0===t&&(t=0),"vertical"==o){var i=s.clientWidth,a=e+t;return r.style.width=u?a/i*100+"%":a+"px"}if("horizontal"==o){var n=s.clientHeight,l=e+t;return r.style.height=u?l/n*100+"%":l+"px"}};n.isResizing=!0;var f=h();n.$emit("paneResizeStart",r,t,f);var g=function(e){var s=e.pageX,u=e.pageY;f="vertical"==o?h(l,s-i):h(c,u-a),n.$emit("paneResize",r,t,f)},m=function(){f=h("vertical"==o?r.clientWidth:r.clientHeight),n.isResizing=!1,p("mousemove",g),p("mouseup",m),n.$emit("paneResizeStop",r,t,f)};d("mousemove",g),d("mouseup",m)}}}};!function(){if("undefined"!=typeof document){var e=document.head||document.getElementsByTagName("head")[0],t=document.createElement("style"),i=".multipane { display: flex; } .multipane.layout-h { flex-direction: column; } .multipane.layout-v { flex-direction: row; } .multipane > div { position: relative; z-index: 1; } .multipane-resizer { display: block; position: relative; z-index: 2; } .layout-h > .multipane-resizer { width: 100%; height: 10px; margin-top: -10px; top: 5px; cursor: row-resize; } .layout-v > .multipane-resizer { width: 10px; height: 100%; margin-left: -10px; left: 5px; cursor: col-resize; } ";t.type="text/css",t.styleSheet?t.styleSheet.cssText=i:t.appendChild(document.createTextNode(i)),e.appendChild(t)}}();var c=Object.assign(l,{render:function(){var e=this,t=e.$createElement;return(e._self._c||t)("div",{class:e.classnames,style:{cursor:e.cursor,userSelect:e.userSelect},on:{mousedown:e.onMouseDown}},[e._t("default")],2)},staticRenderFns:[]});c.prototype=l.prototype,function(){if("undefined"!=typeof document){var e=document.head||document.getElementsByTagName("head")[0],t=document.createElement("style");t.type="text/css",t.styleSheet?t.styleSheet.cssText="":t.appendChild(document.createTextNode("")),e.appendChild(t)}}();var u={render:function(){var e=this,t=e.$createElement;return(e._self._c||t)("div",{staticClass:"multipane-resizer"},[e._t("default")],2)},staticRenderFns:[]};"undefined"!=typeof window&&window.Vue&&(window.Vue.component("multipane",c),window.Vue.component("multipane-resizer",u));var d=i("14Xm"),p=i.n(d),h=i("m1cH"),f=i.n(h),g=i("D3Ub"),m=i.n(g),v=i("P2sY"),b=i.n(v),y={name:"TreeTable",inject:["app"],props:{data:{type:[Array,Object],required:!0},leafFlag:{type:String,default:"leaf"},expandProps:{type:Object,required:!0},mapTypes:{type:Object,default:null},isAsyncExpand:{type:Boolean,default:!0},isInitAll:{type:Boolean,default:!1},isExpandAll:{type:Boolean,default:!1}},computed:{formatData:function(){this.addParent(this.data);return this.isExpandAll||this.isInitAll?this.flattenData(this.data):b()([],this.data)}},methods:{flattenData:function(e){var t=this,i=arguments.length>1&&void 0!==arguments[1]?arguments[1]:[];return e.forEach(function(e,a){i.push(e),e.resources&&t.flattenData(e.resources,i)}),i},addParent:function(e){var t=this,i=arguments.length>1&&void 0!==arguments[1]?arguments[1]:null;e.forEach(function(e,a){t.$set(e,"expanded",t.isExpandAll),i?(e.parent=i,e.level=i.level+1):e.level=1,e.resources&&e.resources.length&&t.addParent(e.resources,e)})},showRow:function(e){var t=e.row,i=!t.parent||t.parent.expanded&&t.parent.show;return t.show=i,i?"":"display:none;"},toggleExpanded:function(e,t){var i=this;return m()(p.a.mark(function a(){var n,s;return p.a.wrap(function(a){for(;;)switch(a.prev=a.next){case 0:if(e[i.leafFlag]){a.next=11;break}if(e.expanded=!e.expanded,!(e.resources&&e.resources.length>0)){a.next=4;break}return a.abrupt("return",i.$nextTick(function(){i.$refs.treeTable.doLayout()}));case 4:return a.next=6,i.app.handleExpandRow(e);case 6:s=a.sent,i.addParent(s,e),e.resources=s,(n=i.formatData).splice.apply(n,[t+1,0].concat(f()(e.resources))),i.$nextTick(function(){i.$refs.treeTable.doLayout()});case 11:case"end":return a.stop()}},a,i)}))()},iconShow:function(e){return!e[this.leafFlag]}}},x=(i("6bP5"),i("yhuy"),i("KHd+")),w=Object(x.a)(y,function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("el-table",e._g(e._b({ref:"treeTable",attrs:{data:e.formatData,"row-style":e.showRow,height:"auto",fit:"",stripe:"",border:"","highlight-current-row":""}},"el-table",e.$attrs,!1),e.$listeners),[e._t("before"),e._v(" "),i("el-table-column",{attrs:{"header-align":"center",label:e.expandProps.name,"min-width":"200"},scopedSlots:e._u([{key:"default",fn:function(t){return[i("div",{staticStyle:{cursor:"pointer"},on:{click:function(i){e.toggleExpanded(t.row,t.$index)}}},[e._l(t.row.level,function(e,t){return i("span",{key:t,staticClass:"ms-tree-space"})}),e._v(" "),e.iconShow(t.row)?i("span",{staticClass:"tree-ctrl"},[t.row.expanded?i("i",{staticClass:"el-icon-caret-bottom"}):i("i",{staticClass:"el-icon-caret-right"}),e._v(" "),i("i",{staticClass:"el-icon-menu"})]):i("span",{staticClass:"tree-ctrl"},[i("i",{staticClass:"el-icon-document"})]),e._v(" "+e._s(e.mapTypes[t.row[e.expandProps.value]])+"\n\t\t\t\t")],2)]}}])}),e._v(" "),e._t("default")],2)},[],!1,null,"528bfa58",null);w.options.__file="index.vue";var C=w.exports,_={name:"TreeTable",inject:["app"],props:{data:{type:[Array,Object],required:!0},leafFlag:{type:String,default:"leaf"},expandProps:{type:Object,required:!0},mapTypes:{type:Object,default:null},isAsyncExpand1:{type:Boolean,default:!0},isInitAll:{type:Boolean,default:!1},isExpandAll1:{type:Boolean,default:!1}},mounted:function(){},computed:{formatData:function(){this.addParent(this.data);return this.isExpandAll||this.isInitAll?this.flattenData(this.data):b()([],this.data)}},methods:{flattenData:function(e){var t=this;console.log("1");var i=[];return function e(t){t.forEach(function(t,a){i.push(t),t.hasOwnProperty("resources")&&Array.isArray(t.resources)&&t.resources.length>0&&e(t.resources)})}(e),setTimeout(function(){console.log(t.formatData),t.formatData.forEach(function(e,i){e.isNegative&&t.$refs.treeTable.toggleRowSelection(t.formatData[i],!0)})},0),i},addParent:function(e){var t=this,i=arguments.length>1&&void 0!==arguments[1]?arguments[1]:null;e.forEach(function(e,a){t.$set(e,"expanded",t.isExpandAll),i?(e.parent=i,e.level=i.level+1):e.level=1,e.resources&&e.resources.length&&t.addParent(e.resources,e)})},showRow:function(e){var t=e.row,i=!t.parent||t.parent.expanded&&t.parent.show;return t.show=i,i?"":"display:none;"},toggleExpanded:function(e,t){var i=this;return m()(p.a.mark(function a(){var n,s;return p.a.wrap(function(a){for(;;)switch(a.prev=a.next){case 0:if(e[i.leafFlag]){a.next=11;break}if(e.expanded=!e.expanded,!(e.resources&&e.resources.length>0)){a.next=4;break}return a.abrupt("return",i.$nextTick(function(){i.$refs.treeTable.doLayout()}));case 4:return a.next=6,i.app.handleExpandRow(e);case 6:s=a.sent,i.addParent(s,e),e.resources=s,(n=i.formatData).splice.apply(n,[t+1,0].concat(f()(e.resources))),i.$nextTick(function(){i.$refs.treeTable.doLayout()});case 11:case"end":return a.stop()}},a,i)}))()},iconShow:function(e){return!e[this.leafFlag]}}},L=(i("OpvW"),i("DIoh"),Object(x.a)(_,function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("el-table",e._g(e._b({ref:"treeTable",attrs:{data:e.formatData,"row-style":e.showRow,height:"300px",fit:"",stripe:"",border:"","highlight-current-row":""}},"el-table",e.$attrs,!1),e.$listeners),[e._t("before"),e._v(" "),i("el-table-column",{attrs:{type:"selection",width:"45"}}),e._v(" "),i("el-table-column",{attrs:{"header-align":"center",label:e.expandProps.name,"min-width":"200"},scopedSlots:e._u([{key:"default",fn:function(t){return[i("div",{staticStyle:{cursor:"pointer"},on:{click:function(i){e.toggleExpanded(t.row,t.$index)}}},[e._l(t.row.level,function(e,t){return i("span",{key:t,staticClass:"ms-tree-space"})}),e._v(" "),e.iconShow(t.row)?i("span",{staticClass:"tree-ctrl"},[t.row.expanded?i("i",{staticClass:"el-icon-caret-bottom"}):i("i",{staticClass:"el-icon-caret-right"}),e._v(" "),i("i",{staticClass:"el-icon-menu"})]):i("span",{staticClass:"tree-ctrl"},[i("i",{staticClass:"el-icon-document"})]),e._v("\n        "+e._s(e.mapTypes[t.row[e.expandProps.value]])+"\n      ")],2)]}}])}),e._v(" "),e._t("default")],2)},[],!1,null,"68462b16",null));L.options.__file="index.vue";var k={name:"roleMaintain",components:{Multipane:c,MultipaneResizer:u,treeTable:C,treeTable1:L.exports},mixins:[r.a],provide:function(){return{app:this}},data:function(){return{props:{label:"resName",children:"resources"},mapTypes:{RC:"富客户端",CAT:"资源分类",PER:"透视图",VIW:"视图",ACT:"Action",WEB:"Web","WEB-CAT":"Web资源分类",FUNC:"Web功能模块",URL:"Web URL资源",DEVICE:"移动终端","DEVICE-CAT":"Device资源分类","DEVICE-VIW":"Device视图",SCREEN:"大屏终端","SCREEN-CAT":"Screen资源分类","SCREEN-VIW":"Screen视图"},trans:!1,rowId:"",delId:"",api:o,isTreeTablePage:!0,isAsyncExpand:!1,isExpandAll:!1,isShrinkRightPane:!1,activeNames:["1","2","3"],isDoubleColumnPage:!0,listQuery:{limit:20,page:1,sort:null,roleCode:"",name:"",description:""},list:[],list1:[],userList:[],resourcelist:[],unselectresourceList:[],unselectactresourceList:[],resouceIdList:[],buttonIdList:[],listTotal:0,listLoading:!1,templateTypeOptions:[],textMap:{view:"查看详情",create:"创建",update:"更新"},dialogVisible:!1,dialogVisible1:!1,dialogStatus:"view",dialogLoading:!1,delButton:!0,addButton:!0,formDisabled:!0,formDisabled1:!0,dialogTemp:{},dialogDetailTemp:{},dialogDetailStatus:"view",tableColumns:[{name:"角色代码",prop:"roleCode"},{name:"角色名称",prop:"name"},{name:"角色描述",prop:"description"}],detailTableColumns:[{name:"用户登录名",prop:"username"},{name:"用户描述",prop:"description"},{name:"真实姓名",prop:"realName"}],detailTableColumns1:[{name:"类型",prop:"resourceType"},{name:"资源名称",prop:"resName"},{name:"资源描述",prop:"resDesc"}]}},created:function(){},methods:{handleCheckResourceChange:function(e,t,i){if(1==t&&0==i)console.log("加入"),this.resouceIdList.push(e.id);else{console.log("去除");var a=this.resouceIdList.indexOf(e.id);a>-1&&this.resouceIdList.splice(a,1)}console.log(this.resouceIdList)},handleCheckButtonChange:function(e,t,i){if(console.log(e,t,i),1==t&&0==i)console.log("加入"),this.buttonIdList.push(e.id);else{console.log("去除");var a=this.buttonIdList.indexOf(e.id);a>-1&&this.buttonIdList.splice(a,1)}console.log(this.buttonIdList)},handleFilter:function(){this.listQuery.page=1,this.delButton=!0,this.addButton=!0,this.fetchData1()},fetchData1:function(){var e=this;this.initWrapRight1(),this.listLoading=!0,this.api.getList(this.listQuery).then(function(t){e.list=t.content,e.listTotal=t.total,e.listLoading=!1}).catch(function(t){e.listLoading=!1})},initWrapRight1:function(){this.isDoubleColumnPage&&(this.dialogStatus="view",this.dialogDetailStatus="view")},handleUpdate1:function(e){var t=this;this.delButton=!0,this.rowId=e.id,this.dialogTemp=e,this.initWrapRight1(),this.dialogLoading=!0,this.dialogStatus="update",this.fieldDisabled=!0,this.api.getDetailById(e.id).then(function(e){t.userList=e,t.dialogLoading=!1,t.addButton=!1,t.$nextTick(function(){t.$refs.dataForm.clearValidate()})}).catch(function(e){t.dialogLoading=!1}),this.api.getResourceDetailById(e.id).then(function(e){t.resourcelist=e,t.dialogLoading=!1,t.$nextTick(function(){})}).catch(function(e){t.dialogLoading=!1})},handleReset:function(){this.listQuery={limit:20,page:1,sort:null,roleCode:"",name:"",description:""}},authorization:function(){var e=this;this.resouceIdList=[],""!=this.rowId&&(this.dialogVisible=!0,this.dialogLoading=!0,this.isAsyncExpand1=!0,this.isExpandAll1=!1,this.api.getunselectresourceById(this.rowId).then(function(t){e.unselectresourceList=t,e.dialogLoading=!1,e.$nextTick(function(){})}).catch(function(t){e.dialogLoading=!1}))},del:function(){var e=this;console.log("删除"),this.api.delresource({roleId:this.rowId,resourceId:this.delId}).then(function(t){e.$message({message:"数据删除成功",type:"success"}),e.api.getResourceDetailById(e.rowId).then(function(t){e.resourcelist=t,e.dialogLoading=!1}).catch(function(t){e.dialogLoading=!1})})},configuration:function(){var e=this;this.buttonIdList=[],this.unselectactresourceList=[],this.dialogVisible1=!0,this.dialogLoading=!0,this.api.getunselectactresourceById(this.rowId).then(function(t){console.log(t),e.unselectactresourceList=t,e.dialogLoading=!1,e.$nextTick(function(){})}).catch(function(t){e.dialogLoading=!1})},expand:function(){var e=this;this.listLoading=!0,this.isAsyncExpand=!1,this.isExpandAll=!0,this.api.getResourceDetailById(this.rowId).then(function(t){e.resourcelist=t,e.listLoading=!1})},collapse:function(){var e=this;this.isAsyncExpand=!0,this.isExpandAll=!1,this.api.getResourceDetailById(this.rowId).then(function(t){e.resourcelist=t,e.dialogLoading=!1}).catch(function(t){e.dialogLoading=!1})},getDelCode:function(e){this.delId=e.id,this.delButton=!1},resouceChoose:function(e){var t=[];e.forEach(function(e,i){t.push(e.id),e.resources!=[]&&null!=e.resources&&e.resources.forEach(function(e,i){t.push(e.id),e.resources!=[]&&null!=e.resources&&e.resources.forEach(function(e,i){t.push(e.id),e.resources!=[]&&null!=e.resources&&e.resources.forEach(function(e,i){t.push(e.id),e.resources!=[]&&null!=e.resources&&e.resources.forEach(function(e,i){t.push(e.id)})})})})}),this.resouceIdList=t},resouceClick:function(e){},buttonClick:function(e){},resouceSure:function(){var e=this;console.log(this.resouceIdList);var t=this.resouceIdList.toString();this.api.eaddresources({roleId:this.rowId,ids:t}).then(function(t){e.$message({message:"数据创建成功",type:"success"}),e.dialogVisible=!1,e.formDisabled=!0,e.api.getResourceDetailById(e.rowId).then(function(t){e.resourcelist=t,e.dialogLoading=!1}).catch(function(t){e.dialogLoading=!1})})},resouceCancel:function(){this.dialogVisible=!1},buttonChoose:function(e){var t=[];e.forEach(function(e,i){t.push(e.id),e.resources!=[]&&null!=e.resources&&e.resources.forEach(function(e,i){t.push(e.id),e.resources!=[]&&null!=e.resources&&e.resources.forEach(function(e,i){t.push(e.id),e.resources!=[]&&null!=e.resources&&e.resources.forEach(function(e,i){t.push(e.id),e.resources!=[]&&null!=e.resources&&e.resources.forEach(function(e,i){t.push(e.id)})})})})}),this.buttonIdList=t},buttonSure:function(){var e=this;console.log(this.buttonIdList);var t=this.buttonIdList.toString();this.api.deniedactionsources({roleId:this.rowId,ids:t}).then(function(t){e.$message({message:"数据创建成功",type:"success"}),e.dialogVisible1=!1,e.api.getResourceDetailById(e.rowId).then(function(t){e.resourcelist=t,e.dialogLoading=!1}).catch(function(t){e.dialogLoading=!1})})},buttonCancel:function(){this.dialogVisible1=!1}}},E=(i("bR9e"),Object(x.a)(k,function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("div",{staticClass:"app-container"},[i("div",{staticClass:"app-search-container",staticStyle:{height:"70px"}},[i("div",{staticClass:"action-title"},[e._v("查询")]),e._v(" "),i("div",{staticClass:"new-search-container"},[i("div",{staticClass:"item-container"},[i("div",{staticClass:"item"},[i("span",{staticClass:"item-title"},[e._v("角色代码")]),e._v(" "),i("div",{staticClass:"item-body"},[i("el-input",{attrs:{size:"mini",placeholder:"请输入"},model:{value:e.listQuery.roleCode,callback:function(t){e.$set(e.listQuery,"roleCode",t)},expression:"listQuery.roleCode"}})],1)]),e._v(" "),i("div",{staticClass:"item"},[i("span",{staticClass:"item-title"},[e._v("角色名称")]),e._v(" "),i("div",{staticClass:"item-body"},[i("el-input",{attrs:{size:"mini",placeholder:"请输入"},model:{value:e.listQuery.name,callback:function(t){e.$set(e.listQuery,"name",t)},expression:"listQuery.name"}})],1)]),e._v(" "),i("div",{staticClass:"item"},[i("span",{staticClass:"item-title"},[e._v("角色描述")]),e._v(" "),i("div",{staticClass:"item-body"},[i("el-input",{attrs:{size:"mini",placeholder:"请输入"},model:{value:e.listQuery.description,callback:function(t){e.$set(e.listQuery,"description",t)},expression:"listQuery.description"}})],1)]),e._v(" "),i("div",{staticClass:"item-button"},[i("el-button",{attrs:{size:"mini",type:"primary",icon:"el-icon-search"},on:{click:e.handleFilter}},[e._v("查询")]),e._v(" "),i("el-button",{attrs:{size:"mini",icon:"el-icon-circle-close"},on:{click:e.handleReset}},[e._v("重置")])],1)])]),e._v(" "),i("div",{staticClass:"divider"})]),e._v(" "),i("div",{staticClass:"app-wrap-container",staticStyle:{height:"calc(100% - 70px)"}},[i("Multipane",{staticClass:"custom-resizer",class:{hideCustomResizer:e.isShrinkRightPane},staticStyle:{height:"100%"},attrs:{layout:"vertical"}},[i("div",{class:{expandLeftPane:e.isShrinkRightPane},style:{width:"50%",minWidth:"200px"}},[i("div",{staticClass:"table-container"},[i("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.listLoading,expression:"listLoading"}],attrs:{data:e.list,height:"auto","element-loading-text":"正在加载中...",fix:"",stripe:"",border:"","highlight-current-row":""},on:{"sort-change":e.handleTableSortChange,"row-click":e.handleUpdate1}},e._l(e.tableColumns,function(e,t){return i("el-table-column",{key:t,attrs:{label:e.name,prop:e.prop,width:e.width,"min-width":"120",align:"left",sortable:"custom"}})}))],1),e._v(" "),i("div",{staticClass:"footer-container"},[i("div",{staticClass:"pagination-center"},[i("el-pagination",{attrs:{"current-page":e.listQuery.page,"page-sizes":[20,50,100,200],"page-size":e.listQuery.limit,layout:"total, sizes, prev, pager, next, jumper","page-count":5,total:e.listTotal},on:{"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}})],1)])]),e._v(" "),i("MultipaneResizer"),e._v(" "),i("div",{class:{shrinkRightPane:e.isShrinkRightPane},style:{flexGrow:1,minWidth:"500px"}},[i("div",{staticClass:"split-right-container"},[i("div",{staticClass:"wrap-title",on:{click:function(t){e.isShrinkRightPane=!0}}},[i("div",{staticClass:"left"},[e._v("详细信息")]),e._v(" "),i("div",{staticClass:"right"},[i("i",{staticClass:"el-icon-arrow-right"})])]),e._v(" "),i("div",{staticClass:"wrap-content"},[i("el-collapse",{model:{value:e.activeNames,callback:function(t){e.activeNames=t},expression:"activeNames"}},[i("el-collapse-item",{attrs:{title:"基础信息",name:"1"}},[i("el-form",{directives:[{name:"loading",rawName:"v-loading",value:e.dialogLoading,expression:"dialogLoading"}],ref:"dataForm",attrs:{"element-loading-text":"数据加载中...",model:e.dialogTemp,"label-position":"left","label-width":"300px",size:"mini"}},e._l(e.tableColumns,function(t,a){return i("el-form-item",{key:a,attrs:{label:t.name,prop:t.prop}},[t.type&&"select"==t.type?i("el-select",{attrs:{clearable:""},model:{value:e.dialogTemp[t.prop],callback:function(i){e.$set(e.dialogTemp,t.prop,i)},expression:"dialogTemp[item.prop]"}},e._l(e.$data[t.options],function(e,t){return i("el-option",{key:t,attrs:{label:e.label,value:e.code}})})):i("el-input",{attrs:{clearable:"",disabled:e.formDisabled1},model:{value:e.dialogTemp[t.prop],callback:function(i){e.$set(e.dialogTemp,t.prop,i)},expression:"dialogTemp[item.prop]"}})],1)}))],1),e._v(" "),i("el-collapse-item",{attrs:{title:"成员用户列表",name:"2"}},[i("el-row",{attrs:{type:"flex"}},[i("el-col",[i("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.listLoading,expression:"listLoading"}],ref:"detailTable",attrs:{data:e.userList,"element-loading-text":"正在加载中...",height:"auto",fix:"",stripe:"",border:"","highlight-current-row":""}},e._l(e.detailTableColumns,function(e,t){return i("el-table-column",{key:t,attrs:{label:e.name,prop:e.prop,width:e.width,"min-width":"120",align:"center"}})}))],1)],1)],1),e._v(" "),i("el-collapse-item",{attrs:{title:"已授权资源列表",name:"3"}},[i("div",{staticClass:"action-container"},[i("el-button",{attrs:{size:"mini",type:"primary",icon:"el-icon-circle-plus",disabled:e.addButton},on:{click:e.authorization}},[e._v("授权资源访问权限")]),e._v(" "),i("el-button",{attrs:{size:"mini",icon:"el-icon-delete",disabled:e.delButton},on:{click:e.del}},[e._v("删除资源访问权限")]),e._v(" "),i("el-button",{attrs:{size:"mini",icon:"el-icon-edit",disabled:e.addButton},on:{click:e.configuration}},[e._v("按钮权限配置")]),e._v(" "),i("el-button",{attrs:{size:"mini",icon:"el-icon-circle-plus-outline",disabled:e.addButton},on:{click:e.expand}}),e._v(" "),i("el-button",{attrs:{size:"mini",icon:"el-icon-remove-outline",disabled:e.addButton},on:{click:e.collapse}})],1),e._v(" "),i("el-row",{attrs:{type:"flex"}},[i("el-col",[i("tree-table",{directives:[{name:"loading",rawName:"v-loading",value:e.listLoading,expression:"listLoading"}],attrs:{data:e.resourcelist,"leaf-flag":"isLeaf","expand-props":{name:"类型",value:"resourceType"},"map-types":e.mapTypes,"is-async-expand":e.isAsyncExpand,"is-expand-all":e.isExpandAll,"is-init-all":!0,"element-loading-text":"正在加载中..."},on:{"row-click":e.getDelCode}},[i("el-table-column",{attrs:{"header-align":"center",label:"资源名称",prop:"resName","min-width":"100"}}),e._v(" "),i("el-table-column",{attrs:{"header-align":"center",label:"资源描述",prop:"resDesc","min-width":"100"}})],1)],1)],1)],1)],1)],1)])])],1),e._v(" "),i("div",{directives:[{name:"show",rawName:"v-show",value:e.isShrinkRightPane,expression:"isShrinkRightPane"}],staticClass:"expandWrapRight"},[i("i",{staticClass:"el-icon-arrow-left",on:{click:function(t){e.isShrinkRightPane=!1}}})])],1),e._v(" "),i("el-dialog",{attrs:{"close-on-click-modal":!1,visible:e.dialogVisible,"append-to-body":!1,"modal-append-to-body":!1,width:"770px",title:"选择资源进行授权"},on:{"update:visible":function(t){e.dialogVisible=t},close:e.handleDialogClose}},[i("div",{staticStyle:{"padding-bottom":"10px"}},[e._v("配置资源权限")]),e._v(" "),i("div",[e._v("选择允许当前角色可以访问的资源进行授权")]),e._v(" "),e._e(),e._v(" "),i("el-tree",{attrs:{data:e.unselectresourceList,"show-checkbox":"",props:e.props},on:{"check-change":e.handleCheckResourceChange}}),e._v(" "),i("div",{staticClass:"dialog-footer",attrs:{slot:"footer",align:"center"},slot:"footer"},[i("el-button",{attrs:{type:"primary"},on:{click:e.resouceSure}},[e._v("确认")]),e._v(" "),i("el-button",{attrs:{type:"primary"},on:{click:e.resouceCancel}},[e._v("取消")])],1)],1),e._v(" "),i("el-dialog",{attrs:{"close-on-click-modal":!1,visible:e.dialogVisible1,"append-to-body":!1,"modal-append-to-body":!1,width:"770px",title:"选择资源进行授权"},on:{"update:visible":function(t){e.dialogVisible1=t},close:e.handleDialogClose}},[i("div",{staticStyle:{"padding-bottom":"10px"}},[e._v("配置按钮权限")]),e._v(" "),i("div",[e._v("选择需要禁止当前角色可使用的按钮")]),e._v(" "),e._e(),e._v(" "),i("el-tree",{attrs:{data:e.unselectactresourceList,"show-checkbox":"",props:e.props,"node-key":"isNegative","default-expand-keys":[!1],"default-checked-keys":[!1]},on:{"check-change":e.handleCheckButtonChange}}),e._v(" "),i("div",{staticClass:"dialog-footer",attrs:{slot:"footer",align:"center"},slot:"footer"},[i("el-button",{attrs:{type:"primary"},on:{click:e.buttonSure}},[e._v("确认")]),e._v(" "),i("el-button",{attrs:{type:"primary"},on:{click:e.buttonCancel}},[e._v("取消")])],1)],1)],1)},[],!1,null,null,null));E.options.__file="index.vue";t.default=E.exports},yhuy:function(e,t,i){"use strict";var a=i("GdYA");i.n(a).a}}]);