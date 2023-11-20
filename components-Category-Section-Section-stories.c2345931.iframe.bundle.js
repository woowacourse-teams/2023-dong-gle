"use strict";(self.webpackChunkfrontend=self.webpackChunkfrontend||[]).push([[489],{"./node_modules/@tanstack/react-query/build/lib/useMutation.mjs":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{D:()=>useMutation});var react=__webpack_require__("./node_modules/react/index.js"),utils=__webpack_require__("./node_modules/@tanstack/query-core/build/lib/utils.mjs"),mutation=__webpack_require__("./node_modules/@tanstack/query-core/build/lib/mutation.mjs"),notifyManager=__webpack_require__("./node_modules/@tanstack/query-core/build/lib/notifyManager.mjs"),subscribable=__webpack_require__("./node_modules/@tanstack/query-core/build/lib/subscribable.mjs");class MutationObserver extends subscribable.l{constructor(client,options){super(),this.client=client,this.setOptions(options),this.bindMethods(),this.updateResult()}bindMethods(){this.mutate=this.mutate.bind(this),this.reset=this.reset.bind(this)}setOptions(options){var _this$currentMutation;const prevOptions=this.options;this.options=this.client.defaultMutationOptions(options),(0,utils.VS)(prevOptions,this.options)||this.client.getMutationCache().notify({type:"observerOptionsUpdated",mutation:this.currentMutation,observer:this}),null==(_this$currentMutation=this.currentMutation)||_this$currentMutation.setOptions(this.options)}onUnsubscribe(){var _this$currentMutation2;this.hasListeners()||(null==(_this$currentMutation2=this.currentMutation)||_this$currentMutation2.removeObserver(this))}onMutationUpdate(action){this.updateResult();const notifyOptions={listeners:!0};"success"===action.type?notifyOptions.onSuccess=!0:"error"===action.type&&(notifyOptions.onError=!0),this.notify(notifyOptions)}getCurrentResult(){return this.currentResult}reset(){this.currentMutation=void 0,this.updateResult(),this.notify({listeners:!0})}mutate(variables,options){return this.mutateOptions=options,this.currentMutation&&this.currentMutation.removeObserver(this),this.currentMutation=this.client.getMutationCache().build(this.client,{...this.options,variables:void 0!==variables?variables:this.options.variables}),this.currentMutation.addObserver(this),this.currentMutation.execute()}updateResult(){const state=this.currentMutation?this.currentMutation.state:(0,mutation.R)(),result={...state,isLoading:"loading"===state.status,isSuccess:"success"===state.status,isError:"error"===state.status,isIdle:"idle"===state.status,mutate:this.mutate,reset:this.reset};this.currentResult=result}notify(options){notifyManager.V.batch((()=>{var _this$mutateOptions$o,_this$mutateOptions,_this$mutateOptions$o2,_this$mutateOptions2;if(this.mutateOptions&&this.hasListeners())if(options.onSuccess)null==(_this$mutateOptions$o=(_this$mutateOptions=this.mutateOptions).onSuccess)||_this$mutateOptions$o.call(_this$mutateOptions,this.currentResult.data,this.currentResult.variables,this.currentResult.context),null==(_this$mutateOptions$o2=(_this$mutateOptions2=this.mutateOptions).onSettled)||_this$mutateOptions$o2.call(_this$mutateOptions2,this.currentResult.data,null,this.currentResult.variables,this.currentResult.context);else if(options.onError){var _this$mutateOptions$o3,_this$mutateOptions3,_this$mutateOptions$o4,_this$mutateOptions4;null==(_this$mutateOptions$o3=(_this$mutateOptions3=this.mutateOptions).onError)||_this$mutateOptions$o3.call(_this$mutateOptions3,this.currentResult.error,this.currentResult.variables,this.currentResult.context),null==(_this$mutateOptions$o4=(_this$mutateOptions4=this.mutateOptions).onSettled)||_this$mutateOptions$o4.call(_this$mutateOptions4,void 0,this.currentResult.error,this.currentResult.variables,this.currentResult.context)}options.listeners&&this.listeners.forEach((({listener})=>{listener(this.currentResult)}))}))}}var useSyncExternalStore=__webpack_require__("./node_modules/@tanstack/react-query/build/lib/useSyncExternalStore.mjs"),QueryClientProvider=__webpack_require__("./node_modules/@tanstack/react-query/build/lib/QueryClientProvider.mjs"),lib_utils=__webpack_require__("./node_modules/@tanstack/react-query/build/lib/utils.mjs");function useMutation(arg1,arg2,arg3){const options=(0,utils.lV)(arg1,arg2,arg3),queryClient=(0,QueryClientProvider.NL)({context:options.context}),[observer]=react.useState((()=>new MutationObserver(queryClient,options)));react.useEffect((()=>{observer.setOptions(options)}),[observer,options]);const result=(0,useSyncExternalStore.$)(react.useCallback((onStoreChange=>observer.subscribe(notifyManager.V.batchCalls(onStoreChange))),[observer]),(()=>observer.getCurrentResult()),(()=>observer.getCurrentResult())),mutate=react.useCallback(((variables,mutateOptions)=>{observer.mutate(variables,mutateOptions).catch(noop)}),[observer]);if(result.error&&(0,lib_utils.L)(observer.options.useErrorBoundary,[result.error]))throw result.error;return{...result,mutate,mutateAsync:result.mutate}}function noop(){}},"./src/components/Category/Section/Section.stories.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.r(__webpack_exports__),__webpack_require__.d(__webpack_exports__,{Playground:()=>Playground,__namedExportsOrder:()=>__namedExportsOrder,default:()=>Section_stories});var styled_components_browser_esm=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),useUncontrolledInput=__webpack_require__("./src/hooks/@common/useUncontrolledInput.ts"),useCategoryMutation=__webpack_require__("./src/components/Category/useCategoryMutation.ts"),Input=__webpack_require__("./src/components/@common/Input/Input.tsx"),icons=__webpack_require__("./src/assets/icons/index.ts"),useToast=__webpack_require__("./src/hooks/@common/useToast.ts"),utils_error=__webpack_require__("./src/utils/error.ts"),validators=__webpack_require__("./src/utils/validators.ts"),jsx_runtime=__webpack_require__("./node_modules/react/jsx-runtime.js");const Header=({onCategoryAdded})=>{const{inputRef,escapeInput:escapeAddCategory,isInputOpen,openInput,resetInput,isError,setIsError}=(0,useUncontrolledInput.Z)(),{addCategory}=(0,useCategoryMutation.Q)(onCategoryAdded),toast=(0,useToast.p)();return(0,jsx_runtime.jsxs)(S.Header,{children:[(0,jsx_runtime.jsx)(S.Title,{children:"카테고리"}),isInputOpen?(0,jsx_runtime.jsx)(Input.Z,{type:"text",variant:"underlined",size:"small",placeholder:"추가할 카테고리",ref:inputRef,isError,onBlur:resetInput,onKeyDown:escapeAddCategory,onKeyUp:async e=>{try{if("Enter"!==e.key)return;const categoryName=e.currentTarget.value.trim();(0,validators.Z)(categoryName),resetInput(),addCategory({categoryName})}catch(error){setIsError(!0),toast.show({type:"error",message:(0,utils_error.e)(error)})}},"aria-label":"카테고리 추가 입력 창"}):(0,jsx_runtime.jsx)(S.Button,{onClick:openInput,"aria-label":"카테고리 추가 입력 창 열기",children:(0,jsx_runtime.jsx)(icons.pO,{width:12,height:12})})]})};Header.displayName="Header";const Header_Header=Header,S={Header:styled_components_browser_esm.zo.header`
    position: sticky;
    top: 0;
    z-index: 1;
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 3.6rem;
    padding: 0.8rem;
    background-color: ${({theme})=>theme.color.spaceBackground};
    font-size: 1.2rem;
    font-weight: 400;
  `,Title:styled_components_browser_esm.zo.h1`
    color: ${({theme})=>theme.color.gray8};
    cursor: default;
  `,Button:styled_components_browser_esm.zo.button`
    display: flex;
    justify-content: center;
    align-items: center;
    width: 2rem;
    height: 2rem;
    border-radius: 4px;

    &:hover {
      background-color: ${({theme})=>theme.color.gray5};
    }
  `};try{Header.displayName="Header",Header.__docgenInfo={description:"",displayName:"Header",props:{onCategoryAdded:{defaultValue:null,description:"",name:"onCategoryAdded",required:!0,type:{name:"() => void"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/Category/Header/Header.tsx#Header"]={docgenInfo:Header.__docgenInfo,name:"Header",path:"src/components/Category/Header/Header.tsx#Header"})}catch(__react_docgen_typescript_loader_error){}var Accordion=__webpack_require__("./src/components/@common/Accordion/Accordion.tsx"),dist=__webpack_require__("./node_modules/@yogjin/react-global-state/dist/index.js"),useQuery=__webpack_require__("./node_modules/@tanstack/react-query/build/lib/useQuery.mjs"),category=__webpack_require__("./src/apis/category.ts"),globalState=__webpack_require__("./src/globalState/index.ts"),react=__webpack_require__("./node_modules/react/index.js");var Category=__webpack_require__("./src/components/Category/Category/Category.tsx"),WritingList=__webpack_require__("./src/components/Category/WritingList/WritingList.tsx"),drag=__webpack_require__("./src/constants/drag.ts");const Item=({categoryId,categoryName,isDefaultCategory,draggingIndexList,dragOverIndexList,onDragStart,onDragEnter,onDragEnd,isWritingDragging})=>{const[isOpen,setIsOpen]=(0,react.useState)(!1);return(0,jsx_runtime.jsx)(Item_S.DragContainer,{draggable:!isDefaultCategory,$draggingTarget:(()=>{const isCategoryDragOverTarget=1===dragOverIndexList.length&&categoryId===dragOverIndexList[drag.k.CATEGORY_ID];return isCategoryDragOverTarget&&dragOverIndexList.length!==draggingIndexList.length?"writing":isCategoryDragOverTarget?"category":"none"})(),onDragStart:onDragStart(categoryId),onDragEnter:onDragEnter(categoryId),onDragEnd,children:(0,jsx_runtime.jsxs)(Accordion.Z.Item,{children:[(0,jsx_runtime.jsx)(Accordion.Z.Title,{onIconClick:()=>setIsOpen((prev=>!prev)),"aria-label":`${categoryName} 카테고리 왼쪽 사이드바에서 열기`,children:(0,jsx_runtime.jsx)(Category.Z,{categoryId,categoryName,isDefaultCategory})}),(0,jsx_runtime.jsx)(Accordion.Z.Panel,{children:(0,jsx_runtime.jsx)(WritingList.Z,{categoryId,isOpen,dragOverIndexList,onDragStart,onDragEnter,onDragEnd,isWritingDragging})})]},categoryId)})};Item.displayName="Item";const Item_Item=Item,Item_S={DragContainer:styled_components_browser_esm.ZP.div`
    position: relative;
    border-top: 0.4rem solid transparent;

    ${({$draggingTarget})=>"category"===$draggingTarget&&styled_components_browser_esm.iv`
        border-radius: 0;
        border-top: 0.4rem solid ${({theme})=>theme.color.dragArea};
      `}

    ${({$draggingTarget})=>"writing"===$draggingTarget&&styled_components_browser_esm.iv`
        &::before {
          content: '';
          pointer-events: none;
          position: absolute;
          width: 100%;
          height: 100%;
          border-radius: 4px;
          background-color: ${({theme})=>theme.color.dragArea};
        }
      `}
  `};try{Item.displayName="Item",Item.__docgenInfo={description:"",displayName:"Item",props:{categoryId:{defaultValue:null,description:"",name:"categoryId",required:!0,type:{name:"number"}},categoryName:{defaultValue:null,description:"",name:"categoryName",required:!0,type:{name:"string"}},isDefaultCategory:{defaultValue:null,description:"",name:"isDefaultCategory",required:!0,type:{name:"boolean"}},draggingIndexList:{defaultValue:null,description:"",name:"draggingIndexList",required:!0,type:{name:"number[]"}},dragOverIndexList:{defaultValue:null,description:"",name:"dragOverIndexList",required:!0,type:{name:"number[]"}},onDragStart:{defaultValue:null,description:"",name:"onDragStart",required:!0,type:{name:"(...ids: number[]) => (e: DragEvent<Element>) => void"}},onDragEnter:{defaultValue:null,description:"",name:"onDragEnter",required:!0,type:{name:"(...ids: number[]) => (e: DragEvent<Element>) => void"}},onDragEnd:{defaultValue:null,description:"",name:"onDragEnd",required:!0,type:{name:"(e: DragEvent<Element>) => void"}},isWritingDragging:{defaultValue:null,description:"",name:"isWritingDragging",required:!0,type:{name:"boolean"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/Category/Item/Item.tsx#Item"]={docgenInfo:Item.__docgenInfo,name:"Item",path:"src/components/Category/Item/Item.tsx#Item"})}catch(__react_docgen_typescript_loader_error){}var QueryClientProvider=__webpack_require__("./node_modules/@tanstack/react-query/build/lib/QueryClientProvider.mjs"),useMutation=__webpack_require__("./node_modules/@tanstack/react-query/build/lib/useMutation.mjs"),writings=__webpack_require__("./src/apis/writings.ts");const useDragAndDrop=()=>{const{updateCategoryOrder}=(0,useCategoryMutation.Q)(),updateWritingOrder=(()=>{const queryClient=(0,QueryClientProvider.NL)();return(0,useMutation.D)(writings.a0,{onSuccess:()=>{queryClient.invalidateQueries(["detailWritings"]),queryClient.invalidateQueries(["writingsInCategory"])}})})(),[draggingIndexList,setDraggingIndexList]=(0,react.useState)([]),[dragOverIndexList,setDragOverIndexList]=(0,react.useState)([]),isCategoryDragging=1===draggingIndexList.length,isWritingDragging=2===draggingIndexList.length;return{draggingIndexList,dragOverIndexList,handleDragStart:(...ids)=>e=>{e.stopPropagation(),setDraggingIndexList(ids)},handleDragEnter:(...ids)=>e=>{e.stopPropagation();const dragOverIndex=ids.slice(0,draggingIndexList.length),isSamePositionDrag=(arr1=draggingIndexList,(arr2=dragOverIndex).length===arr1.length&&arr2.every(((value,idx)=>value===arr1[idx])));var arr1,arr2;const isDefaultCategoryDrag=isCategoryDragging&&dragOverIndex[drag.k.CATEGORY_ID]===Number(localStorage.getItem("defaultCategoryId"));setDragOverIndexList(isSamePositionDrag||isDefaultCategoryDrag?[]:dragOverIndex)},handleDragEnd:e=>{if(e.stopPropagation(),0!==draggingIndexList.length&&0!==dragOverIndexList.length){if(isCategoryDragging)updateCategoryOrder({categoryId:draggingIndexList[drag.k.CATEGORY_ID],body:{nextCategoryId:dragOverIndexList[drag.k.CATEGORY_ID]}});else if(isWritingDragging){const nextWritingId=1===dragOverIndexList.length?drag.j:dragOverIndexList[drag.k.WRITING_ID];updateWritingOrder.mutate({writingId:draggingIndexList[drag.k.WRITING_ID],body:{targetCategoryId:dragOverIndexList[drag.k.CATEGORY_ID],nextWritingId}})}setDraggingIndexList([]),setDragOverIndexList([])}},isCategoryDragging,isWritingDragging}},List=()=>{const{categories}=(()=>{const setActiveCategoryIdState=(0,dist.Bn)(globalState.yk),{data}=(0,useQuery.a)(["categories"],category.CP,{});return(0,react.useEffect)((()=>{if(!data)return;const defaultCategoryId=data.categories[0].id;localStorage.setItem("defaultCategoryId",String(defaultCategoryId)),setActiveCategoryIdState(defaultCategoryId)}),[data]),{categories:data?data.categories:null}})(),{draggingIndexList,dragOverIndexList,handleDragEnd,handleDragEnter,handleDragStart,isCategoryDragging,isWritingDragging}=useDragAndDrop();return categories?(0,jsx_runtime.jsxs)(Accordion.Z,{children:[categories.map(((category,index)=>(0,jsx_runtime.jsx)(Item_Item,{categoryId:category.id,categoryName:category.categoryName,isDefaultCategory:Boolean(0===index),draggingIndexList,dragOverIndexList,onDragStart:handleDragStart,onDragEnter:handleDragEnter,onDragEnd:handleDragEnd,isWritingDragging},category.id))),(0,jsx_runtime.jsx)(List_S.DragLastSection,{onDragEnter:handleDragEnter(drag.j),$isDragOverTarget:isCategoryDragging&&dragOverIndexList[drag.k.CATEGORY_ID]===drag.j})]}):null};List.displayName="List";const List_List=List,List_S={DragLastSection:styled_components_browser_esm.ZP.div`
    height: 0.4rem;
    background-color: transparent;
    ${({$isDragOverTarget})=>$isDragOverTarget&&styled_components_browser_esm.iv`
        background-color: ${({theme})=>theme.color.dragArea};
      `};
  `},throttle=(func,delay)=>{let timer;return(...args)=>{timer||(timer=setTimeout((()=>{timer=null,func(...args)}),delay))}},Section=()=>{const{scrollRef,scrollToBottom,scrollInArea}=(()=>{const scrollRef=(0,react.useRef)(null);return{scrollRef,scrollToBottom:()=>{if(scrollRef.current){const lastItem=scrollRef.current.lastChild;lastItem&&lastItem instanceof HTMLElement&&lastItem.scrollIntoView({behavior:"smooth",block:"end"})}},scrollInArea:(margin,speed)=>e=>{const areaRect=scrollRef.current?.getBoundingClientRect();if(!areaRect)return;let scrollY;scrollY=e.clientY<areaRect.top+margin?Math.floor(Math.max(-1,(e.clientY-areaRect.top)/margin-1)*speed):e.clientY>areaRect.bottom-margin?Math.ceil(Math.min(1,(e.clientY-areaRect.bottom)/margin+1)*speed):0,0!==scrollY&&scrollRef.current&&scrollRef.current.scrollBy({top:scrollY,behavior:"smooth"})}}})();return(0,jsx_runtime.jsxs)(Section_S.Section,{ref:scrollRef,onDrag:throttle(scrollInArea(100,100),100),children:[(0,jsx_runtime.jsx)(Header_Header,{onCategoryAdded:scrollToBottom}),(0,jsx_runtime.jsx)(List_List,{})]})};Section.displayName="Section";const Section_Section=Section,Section_S={Section:styled_components_browser_esm.zo.section`
    width: 26rem;
    overflow: auto;
  `},Section_stories={title:"category/Section",component:Section_Section},Playground={};Playground.parameters={...Playground.parameters,docs:{...Playground.parameters?.docs,source:{originalSource:"{}",...Playground.parameters?.docs?.source}}};const __namedExportsOrder=["Playground"]},"./src/apis/category.ts":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{CP:()=>getCategories,K8:()=>updateCategoryOrder,KD:()=>getWritingsInCategory,i8:()=>addCategory,kQ:()=>updateCategoryTitle,uu:()=>deleteCategory});var constants_apis_url__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./src/constants/apis/url.ts"),_fetch__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./src/apis/fetch.ts");const addCategory=body=>_fetch__WEBPACK_IMPORTED_MODULE_1__.d.post(constants_apis_url__WEBPACK_IMPORTED_MODULE_0__.jA,{json:body}),getCategories=()=>_fetch__WEBPACK_IMPORTED_MODULE_1__.d.get(constants_apis_url__WEBPACK_IMPORTED_MODULE_0__.jA).json(),getWritingsInCategory=categoryId=>_fetch__WEBPACK_IMPORTED_MODULE_1__.d.get(`${constants_apis_url__WEBPACK_IMPORTED_MODULE_0__.jA}/${categoryId}`).json(),updateCategoryTitle=({categoryId,body})=>_fetch__WEBPACK_IMPORTED_MODULE_1__.d.patch(`${constants_apis_url__WEBPACK_IMPORTED_MODULE_0__.jA}/${categoryId}`,{json:body}),updateCategoryOrder=({categoryId,body})=>_fetch__WEBPACK_IMPORTED_MODULE_1__.d.patch(`${constants_apis_url__WEBPACK_IMPORTED_MODULE_0__.jA}/${categoryId}`,{json:body}),deleteCategory=categoryId=>_fetch__WEBPACK_IMPORTED_MODULE_1__.d.delete(`${constants_apis_url__WEBPACK_IMPORTED_MODULE_0__.jA}/${categoryId}`)},"./src/apis/fetch.ts":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{d:()=>fetch_http});var distribution=__webpack_require__("./node_modules/ky/distribution/index.js"),HttpError=__webpack_require__("./src/utils/apis/HttpError.ts"),url=__webpack_require__("./src/constants/apis/url.ts");const fetch_http=distribution.ZP.create({hooks:{beforeError:[error=>{const{response,request,options}=error;return(0,HttpError.S)(response,request,options)}],beforeRequest:[request=>{request.headers.set("Authorization",`Bearer ${localStorage.getItem("accessToken")??""}`)}],afterResponse:[async(request,options,response)=>{if(401===response.status){const{error}=await response.json();if(4011===error.code){const newAccessToken=(await fetch_http.post(`${url.qW}/token/refresh`).json()).accessToken;return localStorage.setItem("accessToken",newAccessToken),request.headers.set("Authorization",`Bearer ${newAccessToken}`),fetch_http(request)}}}]}})},"./src/apis/writings.ts":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{I3:()=>publishWritingToMedium,Jl:()=>getWriting,Yh:()=>updateWritingTitle,_H:()=>getWritingProperties,a0:()=>updateWritingOrder,aU:()=>publishWritingToTistory});var constants_apis_url__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./src/constants/apis/url.ts"),_fetch__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./src/apis/fetch.ts");const getWriting=writingId=>_fetch__WEBPACK_IMPORTED_MODULE_1__.d.get(`${constants_apis_url__WEBPACK_IMPORTED_MODULE_0__.lw}/${writingId}`).json(),getWritingProperties=writingId=>_fetch__WEBPACK_IMPORTED_MODULE_1__.d.get(`${constants_apis_url__WEBPACK_IMPORTED_MODULE_0__.lw}/${writingId}/properties`).json(),publishWritingToTistory=({writingId,body})=>_fetch__WEBPACK_IMPORTED_MODULE_1__.d.post(`${constants_apis_url__WEBPACK_IMPORTED_MODULE_0__.lw}/${writingId}/publish/tistory`,{json:body}),publishWritingToMedium=({writingId,body})=>_fetch__WEBPACK_IMPORTED_MODULE_1__.d.post(`${constants_apis_url__WEBPACK_IMPORTED_MODULE_0__.lw}/${writingId}/publish/medium`,{json:body}),updateWritingTitle=({writingId,body})=>_fetch__WEBPACK_IMPORTED_MODULE_1__.d.patch(`${constants_apis_url__WEBPACK_IMPORTED_MODULE_0__.lw}/${writingId}`,{json:body}),updateWritingOrder=({writingId,body})=>_fetch__WEBPACK_IMPORTED_MODULE_1__.d.patch(`${constants_apis_url__WEBPACK_IMPORTED_MODULE_0__.lw}/${writingId}`,{json:body})},"./src/components/@common/Accordion/Accordion.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{Z:()=>Accordion_Accordion});var styled_components_browser_esm=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),react=__webpack_require__("./node_modules/react/index.js"),icons=__webpack_require__("./src/assets/icons/index.ts"),jsx_runtime=__webpack_require__("./node_modules/react/jsx-runtime.js");const AccordionTitle=({isOpen=!1,onToggleIconClick,onIconClick,onTitleClick,children,...rest})=>(0,jsx_runtime.jsxs)(S.Container,{onClick:onTitleClick,children:[(0,jsx_runtime.jsx)(S.IconButton,{$isOpen:isOpen,onClick:e=>{e.stopPropagation(),onToggleIconClick&&onToggleIconClick(),onIconClick&&onIconClick()},"aria-label":rest["aria-label"],children:(0,jsx_runtime.jsx)(icons.LZ,{width:8,height:14})}),children]});AccordionTitle.displayName="AccordionTitle";const Accordion_AccordionTitle=AccordionTitle,S={Container:styled_components_browser_esm.zo.div`
    display: flex;
    gap: 0.8rem;
    align-items: center;
    width: 100%;
    padding: 0 0.4rem;
    border-radius: 4px;

    &:hover {
      background-color: ${({theme})=>theme.color.gray3};
    }
  `,IconButton:styled_components_browser_esm.zo.button`
    display: flex;
    justify-content: center;
    align-items: center;
    width: 1.8rem;
    height: 2.2rem;
    padding: 0.4rem;
    border-radius: 4px;
    flex-shrink: 0;

    &:hover {
      background-color: ${({theme})=>theme.color.gray5};
    }

    & > svg {
      rotate: ${({$isOpen})=>$isOpen&&"90deg"};
      transition: rotate 0.2s;
    }
  `};try{AccordionTitle.displayName="AccordionTitle",AccordionTitle.__docgenInfo={description:"",displayName:"AccordionTitle",props:{isOpen:{defaultValue:{value:"false"},description:"",name:"isOpen",required:!1,type:{name:"boolean"}},onToggleIconClick:{defaultValue:null,description:"",name:"onToggleIconClick",required:!1,type:{name:"(() => void)"}},onTitleClick:{defaultValue:null,description:"",name:"onTitleClick",required:!1,type:{name:"(() => void)"}},onIconClick:{defaultValue:null,description:"",name:"onIconClick",required:!1,type:{name:"(() => void)"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/@common/Accordion/AccordionTitle.tsx#AccordionTitle"]={docgenInfo:AccordionTitle.__docgenInfo,name:"AccordionTitle",path:"src/components/@common/Accordion/AccordionTitle.tsx#AccordionTitle"})}catch(__react_docgen_typescript_loader_error){}const AccordionPanel=({isOpen=!1,children})=>isOpen?(0,jsx_runtime.jsx)(AccordionPanel_S.Wrapper,{children}):null;AccordionPanel.displayName="AccordionPanel";const Accordion_AccordionPanel=AccordionPanel,AccordionPanel_S={Wrapper:styled_components_browser_esm.zo.div`
    display: flex;
    flex-direction: column;
    width: 100%;
  `};try{AccordionPanel.displayName="AccordionPanel",AccordionPanel.__docgenInfo={description:"",displayName:"AccordionPanel",props:{isOpen:{defaultValue:{value:"false"},description:"",name:"isOpen",required:!1,type:{name:"boolean"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/@common/Accordion/AccordionPanel.tsx#AccordionPanel"]={docgenInfo:AccordionPanel.__docgenInfo,name:"AccordionPanel",path:"src/components/@common/Accordion/AccordionPanel.tsx#AccordionPanel"})}catch(__react_docgen_typescript_loader_error){}const AccordionItem=({children})=>{const[isOpen,setIsOpen]=(0,react.useState)(!1),togglePanel=()=>{setIsOpen(!isOpen)};return(0,jsx_runtime.jsx)(AccordionItem_S.Item,{children:react.Children.map(children,(child=>child&&(0,react.isValidElement)(child)?child.type===Accordion_AccordionTitle?(0,react.cloneElement)(child,{isOpen,onToggleIconClick:togglePanel}):child.type===Accordion_AccordionPanel?(0,react.cloneElement)(child,{isOpen}):void 0:null))})};AccordionItem.displayName="AccordionItem";const Accordion_AccordionItem=AccordionItem,AccordionItem_S={Item:styled_components_browser_esm.zo.li`
    width: 100%;

    border-radius: 4px;
  `};try{AccordionItem.displayName="AccordionItem",AccordionItem.__docgenInfo={description:"",displayName:"AccordionItem",props:{}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/@common/Accordion/AccordionItem.tsx#AccordionItem"]={docgenInfo:AccordionItem.__docgenInfo,name:"AccordionItem",path:"src/components/@common/Accordion/AccordionItem.tsx#AccordionItem"})}catch(__react_docgen_typescript_loader_error){}const Accordion=({size="medium",children,...rest})=>(0,jsx_runtime.jsx)(Accordion_S.List,{size,...rest,children});Accordion.displayName="Accordion";const Accordion_Accordion=Accordion;Accordion.Item=Accordion_AccordionItem,Accordion.Title=Accordion_AccordionTitle,Accordion.Panel=Accordion_AccordionPanel;const Accordion_S={List:styled_components_browser_esm.zo.ul`
    ${({size="medium"})=>(size=>({small:styled_components_browser_esm.iv`
      width: 12rem;
      font-size: 1.2rem;
    `,medium:styled_components_browser_esm.iv`
      width: 24rem;
      font-size: 1.6rem;
    `,large:styled_components_browser_esm.iv`
      width: 36rem;
      font-size: 2rem;
    `}[size]))(size)};

    display: flex;
    flex-direction: column;
    gap: 0.4rem;
    width: 100%;
  `};try{Accordion.displayName="Accordion",Accordion.__docgenInfo={description:"",displayName:"Accordion",props:{size:{defaultValue:{value:"medium"},description:"",name:"size",required:!1,type:{name:"enum",value:[{value:'"small"'},{value:'"medium"'},{value:'"large"'}]}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/@common/Accordion/Accordion.tsx#Accordion"]={docgenInfo:Accordion.__docgenInfo,name:"Accordion",path:"src/components/@common/Accordion/Accordion.tsx#Accordion"})}catch(__react_docgen_typescript_loader_error){}},"./src/components/@common/Input/Input.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{J:()=>InputVariant,Z:()=>__WEBPACK_DEFAULT_EXPORT__});var react__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./node_modules/react/index.js"),styled_components__WEBPACK_IMPORTED_MODULE_2__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./node_modules/react/jsx-runtime.js");const InputVariant=["outline","filled","unstyled","underlined"],Input=({size="medium",labelText,supportingText,variant="outline",isError=!1,...rest},ref)=>{const inputId=(0,react__WEBPACK_IMPORTED_MODULE_0__.useId)();return(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsxs)(S.InputContainer,{children:[labelText&&(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsx)(S.Label,{htmlFor:inputId,$required:rest.required,$variant:variant,children:labelText}),(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsx)(S.Input,{id:inputId,ref,$size:size,$variant:variant,$isError:isError,...rest}),supportingText&&(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsx)(S.SupportingText,{$isError:isError,children:supportingText})]})};Input.displayName="Input";const __WEBPACK_DEFAULT_EXPORT__=(0,react__WEBPACK_IMPORTED_MODULE_0__.forwardRef)(Input),S={InputContainer:styled_components__WEBPACK_IMPORTED_MODULE_2__.zo.div`
    display: flex;
    flex-direction: column;
    gap: 0.6rem;
    font-size: 1.3rem;
  `,Label:styled_components__WEBPACK_IMPORTED_MODULE_2__.zo.label`
    font-weight: 500;
    ${({$required,theme})=>$required&&styled_components__WEBPACK_IMPORTED_MODULE_2__.iv`
        &::after {
          content: '*';
          margin-left: 0.2rem;
          color: ${theme.color.red6};
        }
      `};
  `,Input:styled_components__WEBPACK_IMPORTED_MODULE_2__.zo.input`
    border: none;
    border-radius: 4px;
    background-color: transparent;

    ${({$size})=>{return size=$size,{small:styled_components__WEBPACK_IMPORTED_MODULE_2__.iv`
      padding: 0.6rem 0.6rem;
      font-size: 1.3rem;
    `,medium:styled_components__WEBPACK_IMPORTED_MODULE_2__.iv`
      padding: 0.8rem 1rem;
      font-size: 1.4rem;
    `,large:styled_components__WEBPACK_IMPORTED_MODULE_2__.iv`
      padding: 1rem 1.2rem;
      font-size: 1.5rem;
    `}[size];var size}};
    ${({$variant,$isError})=>{return variant=$variant,isError=$isError,{outline:styled_components__WEBPACK_IMPORTED_MODULE_2__.iv`
      ${({theme})=>styled_components__WEBPACK_IMPORTED_MODULE_2__.iv`
        border: 1px solid ${isError?theme.color.red6:theme.color.gray6};
        outline: 1px solid transparent;

        &:focus {
          border: 1px solid ${isError?theme.color.red6:theme.color.gray6};
          outline: 1px solid ${isError?theme.color.red6:theme.color.gray8};
        }
      `}
    `,filled:styled_components__WEBPACK_IMPORTED_MODULE_2__.iv`
      ${({theme})=>styled_components__WEBPACK_IMPORTED_MODULE_2__.iv`
        background-color: ${isError?theme.color.red1:theme.color.gray4};
        border: 1px solid transparent;
        outline: 1px solid transparent;

        &:focus {
          background-color: transparent;
          outline: 1px solid ${isError?theme.color.red6:theme.color.gray8};
        }
      `}
    `,unstyled:styled_components__WEBPACK_IMPORTED_MODULE_2__.iv`
      ${({theme})=>styled_components__WEBPACK_IMPORTED_MODULE_2__.iv`
        border: 1px solid transparent;
        outline: 1px solid ${isError?theme.color.red6:theme.color.gray1};
      `}
    `,underlined:styled_components__WEBPACK_IMPORTED_MODULE_2__.iv`
      ${({theme})=>styled_components__WEBPACK_IMPORTED_MODULE_2__.iv`
        border: 1px solid transparent;
        border-bottom: 1px solid ${isError?theme.color.red6:theme.color.gray6};
        border-radius: 0;
        outline: 1px solid transparent;
      `}
    `}[variant];var variant,isError}};

    &::placeholder {
      color: ${({theme})=>theme.color.gray6};
    }
  `,SupportingText:styled_components__WEBPACK_IMPORTED_MODULE_2__.zo.p`
    color: ${({$isError,theme})=>$isError?theme.color.red6:theme.color.gray7};
  `,Underline:styled_components__WEBPACK_IMPORTED_MODULE_2__.zo.div`
    position: absolute;
    bottom: 0;
    left: 0;
    height: 2px;
    width: 100%;
    background-color: ${({theme})=>theme.color.primary};
    transform: scaleX(0);
    transition: all 0.3s ease;
  `}},"./src/components/Category/Category/Category.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{Z:()=>__WEBPACK_DEFAULT_EXPORT__});var assets_icons__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./src/assets/icons/index.ts"),hooks_usePageNavigate__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./src/hooks/usePageNavigate.ts"),styled_components__WEBPACK_IMPORTED_MODULE_10__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),_hooks_common_useUncontrolledInput__WEBPACK_IMPORTED_MODULE_2__=__webpack_require__("./src/hooks/@common/useUncontrolledInput.ts"),_useCategoryMutation__WEBPACK_IMPORTED_MODULE_3__=__webpack_require__("./src/components/Category/useCategoryMutation.ts"),components_common_Input_Input__WEBPACK_IMPORTED_MODULE_4__=__webpack_require__("./src/components/@common/Input/Input.tsx"),components_DeleteButton_DeleteButton__WEBPACK_IMPORTED_MODULE_5__=__webpack_require__("./src/components/DeleteButton/DeleteButton.tsx"),hooks_common_useToast__WEBPACK_IMPORTED_MODULE_6__=__webpack_require__("./src/hooks/@common/useToast.ts"),utils_error__WEBPACK_IMPORTED_MODULE_9__=__webpack_require__("./src/utils/error.ts"),utils_validators__WEBPACK_IMPORTED_MODULE_8__=__webpack_require__("./src/utils/validators.ts"),react_jsx_runtime__WEBPACK_IMPORTED_MODULE_7__=__webpack_require__("./node_modules/react/jsx-runtime.js");const Category=({categoryId,categoryName,isDefaultCategory})=>{const{inputRef,escapeInput:escapeRename,isInputOpen,openInput,resetInput,isError,setIsError}=(0,_hooks_common_useUncontrolledInput__WEBPACK_IMPORTED_MODULE_2__.Z)(),{updateCategoryTitle,deleteCategory}=(0,_useCategoryMutation__WEBPACK_IMPORTED_MODULE_3__.Q)(),{goWritingTablePage}=(0,hooks_usePageNavigate__WEBPACK_IMPORTED_MODULE_1__.L)(),toast=(0,hooks_common_useToast__WEBPACK_IMPORTED_MODULE_6__.p)();return(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_7__.jsx)(S.Container,{$isDefaultCategory:isDefaultCategory,children:isInputOpen?(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_7__.jsx)(components_common_Input_Input__WEBPACK_IMPORTED_MODULE_4__.Z,{type:"text",variant:"underlined",size:"small",placeholder:"변경할 카테고리 이름",defaultValue:categoryName,ref:inputRef,isError,onBlur:resetInput,onKeyDown:escapeRename,onKeyUp:e=>{try{if("Enter"!==e.key)return;const categoryName=e.currentTarget.value.trim();(0,utils_validators__WEBPACK_IMPORTED_MODULE_8__.Z)(categoryName),updateCategoryTitle({categoryId,body:{categoryName}}),resetInput()}catch(error){setIsError(!0),toast.show({type:"error",message:(0,utils_error__WEBPACK_IMPORTED_MODULE_9__.e)(error)})}},"aria-label":`${categoryName} 카테고리 이름 수정 입력 창`}):(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_7__.jsxs)(react_jsx_runtime__WEBPACK_IMPORTED_MODULE_7__.Fragment,{children:[(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_7__.jsx)(S.CategoryButton,{onClick:()=>goWritingTablePage(categoryId),"aria-label":`${categoryName} 카테고리 메인 화면에 열기`,children:(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_7__.jsx)(S.Text,{children:categoryName})}),!isDefaultCategory&&(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_7__.jsxs)(S.IconContainer,{children:[(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_7__.jsx)(S.Button,{"aria-label":`${categoryName} 카테고리 이름 수정`,onClick:openInput,children:(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_7__.jsx)(assets_icons__WEBPACK_IMPORTED_MODULE_0__.vd,{width:12,height:12})}),(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_7__.jsx)(components_DeleteButton_DeleteButton__WEBPACK_IMPORTED_MODULE_5__.Z,{"aria-label":`${categoryName} 카테고리 삭제`,onClick:()=>deleteCategory(categoryId)})]})]})})};Category.displayName="Category";const __WEBPACK_DEFAULT_EXPORT__=Category,S={Container:styled_components__WEBPACK_IMPORTED_MODULE_10__.zo.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 90%;
    height: 3.6rem;
    border-radius: 4px;
    font-size: 1.4rem;

    &:hover {
      & > button {
        padding-right: ${({$isDefaultCategory})=>!$isDefaultCategory&&"5.2rem"};
      }

      div {
        opacity: 0.99;
      }
    }
  `,CategoryButton:styled_components__WEBPACK_IMPORTED_MODULE_10__.zo.button`
    flex: 1;
    min-width: 0;
    height: 100%;
    text-align: left;
  `,Text:styled_components__WEBPACK_IMPORTED_MODULE_10__.zo.p`
    color: ${({theme})=>theme.color.gray10};
    font-weight: 600;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  `,Input:styled_components__WEBPACK_IMPORTED_MODULE_10__.zo.input`
    border: none;
    outline: none;
    color: ${({theme})=>theme.color.gray10};
    font-size: 1.3rem;
    font-weight: 600;

    &::placeholder {
      font-weight: 300;
    }
  `,IconContainer:styled_components__WEBPACK_IMPORTED_MODULE_10__.zo.div`
    display: flex;
    position: absolute;
    right: 0;
    margin-right: 0.8rem;
    opacity: 0;
  `,Button:styled_components__WEBPACK_IMPORTED_MODULE_10__.zo.button`
    display: flex;
    justify-content: center;
    align-items: center;
    width: 2rem;
    height: 2.4rem;
    padding: 0.4rem;
    border-radius: 4px;

    &:hover {
      background-color: ${({theme})=>theme.color.gray5};
    }
  `};try{Category.displayName="Category",Category.__docgenInfo={description:"",displayName:"Category",props:{categoryId:{defaultValue:null,description:"",name:"categoryId",required:!0,type:{name:"number"}},categoryName:{defaultValue:null,description:"",name:"categoryName",required:!0,type:{name:"string"}},isDefaultCategory:{defaultValue:null,description:"",name:"isDefaultCategory",required:!0,type:{name:"boolean"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/Category/Category/Category.tsx#Category"]={docgenInfo:Category.__docgenInfo,name:"Category",path:"src/components/Category/Category/Category.tsx#Category"})}catch(__react_docgen_typescript_loader_error){}},"./src/components/Category/WritingList/WritingList.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{Z:()=>WritingList_WritingList});var icons=__webpack_require__("./src/assets/icons/index.ts"),usePageNavigate=__webpack_require__("./src/hooks/usePageNavigate.ts"),styled_components_browser_esm=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),category=__webpack_require__("./src/apis/category.ts"),useQuery=__webpack_require__("./node_modules/@tanstack/react-query/build/lib/useQuery.mjs");var DeleteButton=__webpack_require__("./src/components/DeleteButton/DeleteButton.tsx"),QueryClientProvider=__webpack_require__("./node_modules/@tanstack/react-query/build/lib/QueryClientProvider.mjs"),useMutation=__webpack_require__("./node_modules/@tanstack/react-query/build/lib/useMutation.mjs"),url=__webpack_require__("./src/constants/apis/url.ts"),fetch=__webpack_require__("./src/apis/fetch.ts");const moveToTrash=writingIds=>{const json={writingIds,isPermanentDelete:!1};return fetch.d.post(url.zP,{json})};var useToast=__webpack_require__("./src/hooks/@common/useToast.ts"),HttpError=__webpack_require__("./src/utils/apis/HttpError.ts");var dist=__webpack_require__("./node_modules/@yogjin/react-global-state/dist/index.js"),globalState=__webpack_require__("./src/globalState/index.ts"),drag=__webpack_require__("./src/constants/drag.ts"),jsx_runtime=__webpack_require__("./node_modules/react/jsx-runtime.js");const WritingList=({categoryId,isOpen,dragOverIndexList,onDragStart,onDragEnter,onDragEnd,isWritingDragging})=>{const{goWritingPage}=(0,usePageNavigate.L)(),{writings}=((categoryId,isOpen)=>{const{data}=(0,useQuery.a)(["writingsInCategory",categoryId],(()=>(0,category.KD)(categoryId)),{enabled:Boolean(isOpen)});return{writings:data?data.writings:null}})(categoryId,isOpen),activeWritingInfo=(0,dist.aO)(globalState.Jc),writingId=activeWritingInfo?.id,deleteWritings=(()=>{const queryClient=(0,QueryClientProvider.NL)(),{goTrashCanPage}=(0,usePageNavigate.L)(),toast=(0,useToast.p)(),{mutate}=(0,useMutation.D)(moveToTrash,{onSuccess:()=>{toast.show({type:"success",message:"글이 휴지통으로 이동됐습니다."}),queryClient.invalidateQueries(["deletedWritings"]),queryClient.invalidateQueries(["writingsInCategory"]),goTrashCanPage()},onError:error=>{error instanceof HttpError.o&&toast.show({type:"error",message:error.message})}});return writingIds=>{confirm("글을 삭제하시겠습니까?")&&mutate(writingIds)}})(),isWritingDragOverTarget=(categoryId,writingId)=>isWritingDragging&&categoryId===dragOverIndexList[drag.k.CATEGORY_ID]&&writingId===dragOverIndexList[drag.k.WRITING_ID];return writings&&0!==writings?.length?(0,jsx_runtime.jsxs)("ul",{children:[writings.map((writing=>(0,jsx_runtime.jsxs)(S.Item,{$isClicked:writingId===writing.id,$isDragOverTarget:isWritingDragOverTarget(categoryId,writing.id),draggable:!0,onDragStart:onDragStart(categoryId,writing.id),onDragEnter:onDragEnter(categoryId,writing.id),onDragEnd,children:[(0,jsx_runtime.jsxs)(S.Button,{"aria-label":`${writing.title}글 메인화면에 열기`,onClick:()=>goWritingPage({categoryId,writingId:writing.id,isDeletedWriting:!1}),children:[(0,jsx_runtime.jsx)(S.IconWrapper,{children:(0,jsx_runtime.jsx)(icons.C,{width:14,height:14})}),(0,jsx_runtime.jsx)(S.Text,{children:writing.title})]}),(0,jsx_runtime.jsx)(S.DeleteButtonWrapper,{children:(0,jsx_runtime.jsx)(DeleteButton.Z,{onClick:()=>deleteWritings([writing.id]),"aria-label":`${writing.title}글 삭제`})})]},writing.id))),(0,jsx_runtime.jsx)(S.DragLastSection,{onDragEnter:onDragEnter(categoryId,drag.j),$isDragOverTarget:isWritingDragOverTarget(categoryId,drag.j)})]}):(0,jsx_runtime.jsx)(S.NoWritingsText,{children:"빈 카테고리"})};WritingList.displayName="WritingList";const WritingList_WritingList=WritingList,S={Item:styled_components_browser_esm.zo.li`
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    height: 3.6rem;
    border-radius: 4px;
    background-color: ${({theme,$isClicked})=>$isClicked&&theme.color.gray4};
    border-top: 0.4rem solid transparent;

    ${({$isDragOverTarget})=>$isDragOverTarget&&styled_components_browser_esm.iv`
        border-radius: 0;
        border-top: 0.4rem solid ${({theme})=>theme.color.dragArea};
      `};
    &:hover {
      background-color: ${({theme})=>theme.color.gray3};

      & > button {
        padding-right: 2.8rem;
      }

      div {
        opacity: 0.99;
      }
    }
  `,Button:styled_components_browser_esm.zo.button`
    display: flex;
    align-items: center;
    gap: 0.4rem;
    width: 100%;
    min-width: 0;
    height: 100%;
    padding: 0.4rem 0 0.4rem 3.2rem;
    border-radius: 4px;
  `,IconWrapper:styled_components_browser_esm.zo.div`
    flex-shrink: 0;
  `,Text:styled_components_browser_esm.zo.p`
    color: ${({theme})=>theme.color.gray9};
    font-size: 1.4rem;
    font-weight: 400;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  `,NoWritingsText:styled_components_browser_esm.zo.p`
    padding: 0.4rem 0 0.4rem 3.2rem;
    color: ${({theme})=>theme.color.gray6};
    font-size: 1.4rem;
    font-weight: 500;
    cursor: default;
  `,DeleteButtonWrapper:styled_components_browser_esm.zo.div`
    position: absolute;
    right: 0;
    margin-right: 0.8rem;
    opacity: 0;
  `,DragLastSection:styled_components_browser_esm.zo.div`
    height: 0.4rem;
    background-color: transparent;
    ${({$isDragOverTarget})=>$isDragOverTarget&&styled_components_browser_esm.iv`
        background-color: ${({theme})=>theme.color.dragArea};
      `};
  `};try{WritingList.displayName="WritingList",WritingList.__docgenInfo={description:"",displayName:"WritingList",props:{categoryId:{defaultValue:null,description:"",name:"categoryId",required:!0,type:{name:"number"}},isOpen:{defaultValue:null,description:"",name:"isOpen",required:!0,type:{name:"boolean"}},dragOverIndexList:{defaultValue:null,description:"",name:"dragOverIndexList",required:!0,type:{name:"number[]"}},onDragStart:{defaultValue:null,description:"",name:"onDragStart",required:!0,type:{name:"(...ids: number[]) => (e: DragEvent<Element>) => void"}},onDragEnter:{defaultValue:null,description:"",name:"onDragEnter",required:!0,type:{name:"(...ids: number[]) => (e: DragEvent<Element>) => void"}},onDragEnd:{defaultValue:null,description:"",name:"onDragEnd",required:!0,type:{name:"(e: DragEvent<Element>) => void"}},isWritingDragging:{defaultValue:null,description:"",name:"isWritingDragging",required:!0,type:{name:"boolean"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/Category/WritingList/WritingList.tsx#WritingList"]={docgenInfo:WritingList.__docgenInfo,name:"WritingList",path:"src/components/Category/WritingList/WritingList.tsx#WritingList"})}catch(__react_docgen_typescript_loader_error){}},"./src/components/Category/useCategoryMutation.ts":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{Q:()=>useCategoryMutation});var _tanstack_react_query__WEBPACK_IMPORTED_MODULE_2__=__webpack_require__("./node_modules/@tanstack/react-query/build/lib/QueryClientProvider.mjs"),_tanstack_react_query__WEBPACK_IMPORTED_MODULE_3__=__webpack_require__("./node_modules/@tanstack/react-query/build/lib/useMutation.mjs"),apis_category__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./src/apis/category.ts"),hooks_common_useToast__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./src/hooks/@common/useToast.ts"),utils_error__WEBPACK_IMPORTED_MODULE_4__=__webpack_require__("./src/utils/error.ts");const useCategoryMutation=onCategoryAdded=>{const queryClient=(0,_tanstack_react_query__WEBPACK_IMPORTED_MODULE_2__.NL)(),toast=(0,hooks_common_useToast__WEBPACK_IMPORTED_MODULE_1__.p)(),{mutate:addCategory}=(0,_tanstack_react_query__WEBPACK_IMPORTED_MODULE_3__.D)(apis_category__WEBPACK_IMPORTED_MODULE_0__.i8,{onSuccess:async()=>{await queryClient.invalidateQueries(["categories"]),setTimeout((()=>{onCategoryAdded?.()}))},onError:error=>{toast.show({type:"error",message:(0,utils_error__WEBPACK_IMPORTED_MODULE_4__.e)(error)})}}),{mutate:updateCategoryTitle}=(0,_tanstack_react_query__WEBPACK_IMPORTED_MODULE_3__.D)(apis_category__WEBPACK_IMPORTED_MODULE_0__.kQ,{onSuccess:()=>{queryClient.invalidateQueries(["categories"]),queryClient.invalidateQueries(["detailWritings"])}}),{mutate:updateCategoryOrder}=(0,_tanstack_react_query__WEBPACK_IMPORTED_MODULE_3__.D)(apis_category__WEBPACK_IMPORTED_MODULE_0__.K8,{onSuccess:()=>{queryClient.invalidateQueries(["categories"])}}),{mutate:deleteCategory}=(0,_tanstack_react_query__WEBPACK_IMPORTED_MODULE_3__.D)(apis_category__WEBPACK_IMPORTED_MODULE_0__.uu,{onSuccess:()=>{queryClient.invalidateQueries(["categories"]),queryClient.invalidateQueries(["writingsInCategory"])}});return{addCategory,updateCategoryTitle,updateCategoryOrder,deleteCategory}}},"./src/components/DeleteButton/DeleteButton.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{Z:()=>__WEBPACK_DEFAULT_EXPORT__});var assets_icons__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./src/assets/icons/index.ts"),styled_components__WEBPACK_IMPORTED_MODULE_2__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./node_modules/react/jsx-runtime.js");const DeleteButton=({...rest})=>(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsx)(S.Button,{...rest,children:(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsx)(assets_icons__WEBPACK_IMPORTED_MODULE_0__.Ms,{width:12,height:12,"aria-label":"휴지통 아이콘"})});DeleteButton.displayName="DeleteButton";const __WEBPACK_DEFAULT_EXPORT__=DeleteButton,S={Button:styled_components__WEBPACK_IMPORTED_MODULE_2__.zo.button`
    display: flex;
    justify-content: center;
    align-items: center;
    width: 2rem;
    height: 2.4rem;
    border-radius: 4px;

    &:hover {
      background-color: ${({theme})=>theme.color.gray5};
    }
  `};try{DeleteButton.displayName="DeleteButton",DeleteButton.__docgenInfo={description:"",displayName:"DeleteButton",props:{}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/DeleteButton/DeleteButton.tsx#DeleteButton"]={docgenInfo:DeleteButton.__docgenInfo,name:"DeleteButton",path:"src/components/DeleteButton/DeleteButton.tsx#DeleteButton"})}catch(__react_docgen_typescript_loader_error){}},"./src/constants/drag.ts":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{j:()=>LAST_DRAG_SECTION_ID,k:()=>INDEX_POSITION});const LAST_DRAG_SECTION_ID=-1,INDEX_POSITION={CATEGORY_ID:0,WRITING_ID:1}},"./src/hooks/@common/useUncontrolledInput.ts":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{Z:()=>__WEBPACK_DEFAULT_EXPORT__});var react__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./node_modules/react/index.js");const __WEBPACK_DEFAULT_EXPORT__=()=>{const[isError,setIsError]=(0,react__WEBPACK_IMPORTED_MODULE_0__.useState)(!1),[isInputOpen,setIsInputOpen]=(0,react__WEBPACK_IMPORTED_MODULE_0__.useState)(!1),inputRef=(0,react__WEBPACK_IMPORTED_MODULE_0__.useRef)(null);(0,react__WEBPACK_IMPORTED_MODULE_0__.useEffect)((()=>{inputRef.current&&inputRef.current.focus()}),[isInputOpen]);const resetInput=()=>{setIsError(!1),setIsInputOpen(!1)};return{inputRef,escapeInput:e=>{"Escape"===e.key&&resetInput()},isInputOpen,openInput:()=>setIsInputOpen(!0),resetInput,isError,setIsError}}},"./src/utils/error.ts":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{e:()=>getErrorMessage});const getErrorMessage=error=>{if(error instanceof Error)return error.message}},"./src/utils/validators.ts":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{Z:()=>validateCategoryName,d:()=>validateWritingTitle});const validateCategoryName=categoryName=>{if(!(0<categoryName.length&&categoryName.length<=30))throw new Error("카테고리 이름은 1자 이상 30자 이하로 입력해주세요.")},validateWritingTitle=writingTitle=>{if(!(0<writingTitle.length&&writingTitle.length<=255))throw new Error("글 제목은 1자 이상 255자 이하로 입력해주세요.")}}}]);
//# sourceMappingURL=components-Category-Section-Section-stories.c2345931.iframe.bundle.js.map