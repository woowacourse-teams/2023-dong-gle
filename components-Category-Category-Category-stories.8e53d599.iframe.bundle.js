"use strict";(self.webpackChunkfrontend=self.webpackChunkfrontend||[]).push([[325],{"./node_modules/@tanstack/react-query/build/lib/useMutation.mjs":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{D:()=>useMutation});var react=__webpack_require__("./node_modules/react/index.js"),utils=__webpack_require__("./node_modules/@tanstack/query-core/build/lib/utils.mjs"),mutation=__webpack_require__("./node_modules/@tanstack/query-core/build/lib/mutation.mjs"),notifyManager=__webpack_require__("./node_modules/@tanstack/query-core/build/lib/notifyManager.mjs"),subscribable=__webpack_require__("./node_modules/@tanstack/query-core/build/lib/subscribable.mjs");class MutationObserver extends subscribable.l{constructor(client,options){super(),this.client=client,this.setOptions(options),this.bindMethods(),this.updateResult()}bindMethods(){this.mutate=this.mutate.bind(this),this.reset=this.reset.bind(this)}setOptions(options){var _this$currentMutation;const prevOptions=this.options;this.options=this.client.defaultMutationOptions(options),(0,utils.VS)(prevOptions,this.options)||this.client.getMutationCache().notify({type:"observerOptionsUpdated",mutation:this.currentMutation,observer:this}),null==(_this$currentMutation=this.currentMutation)||_this$currentMutation.setOptions(this.options)}onUnsubscribe(){var _this$currentMutation2;this.hasListeners()||(null==(_this$currentMutation2=this.currentMutation)||_this$currentMutation2.removeObserver(this))}onMutationUpdate(action){this.updateResult();const notifyOptions={listeners:!0};"success"===action.type?notifyOptions.onSuccess=!0:"error"===action.type&&(notifyOptions.onError=!0),this.notify(notifyOptions)}getCurrentResult(){return this.currentResult}reset(){this.currentMutation=void 0,this.updateResult(),this.notify({listeners:!0})}mutate(variables,options){return this.mutateOptions=options,this.currentMutation&&this.currentMutation.removeObserver(this),this.currentMutation=this.client.getMutationCache().build(this.client,{...this.options,variables:void 0!==variables?variables:this.options.variables}),this.currentMutation.addObserver(this),this.currentMutation.execute()}updateResult(){const state=this.currentMutation?this.currentMutation.state:(0,mutation.R)(),result={...state,isLoading:"loading"===state.status,isSuccess:"success"===state.status,isError:"error"===state.status,isIdle:"idle"===state.status,mutate:this.mutate,reset:this.reset};this.currentResult=result}notify(options){notifyManager.V.batch((()=>{var _this$mutateOptions$o,_this$mutateOptions,_this$mutateOptions$o2,_this$mutateOptions2;if(this.mutateOptions&&this.hasListeners())if(options.onSuccess)null==(_this$mutateOptions$o=(_this$mutateOptions=this.mutateOptions).onSuccess)||_this$mutateOptions$o.call(_this$mutateOptions,this.currentResult.data,this.currentResult.variables,this.currentResult.context),null==(_this$mutateOptions$o2=(_this$mutateOptions2=this.mutateOptions).onSettled)||_this$mutateOptions$o2.call(_this$mutateOptions2,this.currentResult.data,null,this.currentResult.variables,this.currentResult.context);else if(options.onError){var _this$mutateOptions$o3,_this$mutateOptions3,_this$mutateOptions$o4,_this$mutateOptions4;null==(_this$mutateOptions$o3=(_this$mutateOptions3=this.mutateOptions).onError)||_this$mutateOptions$o3.call(_this$mutateOptions3,this.currentResult.error,this.currentResult.variables,this.currentResult.context),null==(_this$mutateOptions$o4=(_this$mutateOptions4=this.mutateOptions).onSettled)||_this$mutateOptions$o4.call(_this$mutateOptions4,void 0,this.currentResult.error,this.currentResult.variables,this.currentResult.context)}options.listeners&&this.listeners.forEach((({listener})=>{listener(this.currentResult)}))}))}}var useSyncExternalStore=__webpack_require__("./node_modules/@tanstack/react-query/build/lib/useSyncExternalStore.mjs"),QueryClientProvider=__webpack_require__("./node_modules/@tanstack/react-query/build/lib/QueryClientProvider.mjs"),lib_utils=__webpack_require__("./node_modules/@tanstack/react-query/build/lib/utils.mjs");function useMutation(arg1,arg2,arg3){const options=(0,utils.lV)(arg1,arg2,arg3),queryClient=(0,QueryClientProvider.NL)({context:options.context}),[observer]=react.useState((()=>new MutationObserver(queryClient,options)));react.useEffect((()=>{observer.setOptions(options)}),[observer,options]);const result=(0,useSyncExternalStore.$)(react.useCallback((onStoreChange=>observer.subscribe(notifyManager.V.batchCalls(onStoreChange))),[observer]),(()=>observer.getCurrentResult()),(()=>observer.getCurrentResult())),mutate=react.useCallback(((variables,mutateOptions)=>{observer.mutate(variables,mutateOptions).catch(noop)}),[observer]);if(result.error&&(0,lib_utils.L)(observer.options.useErrorBoundary,[result.error]))throw result.error;return{...result,mutate,mutateAsync:result.mutate}}function noop(){}},"./src/components/Category/Category/Category.stories.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.r(__webpack_exports__),__webpack_require__.d(__webpack_exports__,{LongCategoryName:()=>LongCategoryName,Playground:()=>Playground,__namedExportsOrder:()=>__namedExportsOrder,default:()=>__WEBPACK_DEFAULT_EXPORT__});var _Category__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./src/components/Category/Category/Category.tsx"),styles_storybook__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./src/styles/storybook.ts"),react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__=__webpack_require__("./node_modules/react/jsx-runtime.js");const __WEBPACK_DEFAULT_EXPORT__={title:"category/Category",component:_Category__WEBPACK_IMPORTED_MODULE_0__.Z,args:{categoryName:"프로젝트 기록"},argTypes:{categoryName:{description:"카테고리의 제목입니다.",control:{type:"text"}}}},Playground={},LongCategoryName={render:()=>(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)(styles_storybook__WEBPACK_IMPORTED_MODULE_1__.y1,{children:["가","가나","가나다","가나다라","가나다라마","가나다라마바사아자차카타파하","가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하"].map((name=>(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)(styles_storybook__WEBPACK_IMPORTED_MODULE_1__.CA,{children:(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)(_Category__WEBPACK_IMPORTED_MODULE_0__.Z,{categoryId:1,categoryName:name,isDefaultCategory:!1})})))})};Playground.parameters={...Playground.parameters,docs:{...Playground.parameters?.docs,source:{originalSource:"{}",...Playground.parameters?.docs?.source}}},LongCategoryName.parameters={...LongCategoryName.parameters,docs:{...LongCategoryName.parameters?.docs,source:{originalSource:"{\n  render: () => {\n    const names = ['가', '가나', '가나다', '가나다라', '가나다라마', '가나다라마바사아자차카타파하', '가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하'];\n    return <StoryContainer>\n        {names.map(name => <StoryItemContainer>\n            <Category categoryId={1} categoryName={name} isDefaultCategory={false} />\n          </StoryItemContainer>)}\n      </StoryContainer>;\n  }\n}",...LongCategoryName.parameters?.docs?.source}}};const __namedExportsOrder=["Playground","LongCategoryName"]},"./src/apis/category.ts":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{CP:()=>getCategories,K8:()=>updateCategoryOrder,KD:()=>getWritingsInCategory,i8:()=>addCategory,kQ:()=>updateCategoryTitle,uu:()=>deleteCategory});var constants_apis_url__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./src/constants/apis/url.ts"),_fetch__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./src/apis/fetch.ts");const addCategory=body=>_fetch__WEBPACK_IMPORTED_MODULE_1__.d.post(constants_apis_url__WEBPACK_IMPORTED_MODULE_0__.jA,{json:body}),getCategories=()=>_fetch__WEBPACK_IMPORTED_MODULE_1__.d.get(constants_apis_url__WEBPACK_IMPORTED_MODULE_0__.jA).json(),getWritingsInCategory=categoryId=>_fetch__WEBPACK_IMPORTED_MODULE_1__.d.get(`${constants_apis_url__WEBPACK_IMPORTED_MODULE_0__.jA}/${categoryId}`).json(),updateCategoryTitle=({categoryId,body})=>_fetch__WEBPACK_IMPORTED_MODULE_1__.d.patch(`${constants_apis_url__WEBPACK_IMPORTED_MODULE_0__.jA}/${categoryId}`,{json:body}),updateCategoryOrder=({categoryId,body})=>_fetch__WEBPACK_IMPORTED_MODULE_1__.d.patch(`${constants_apis_url__WEBPACK_IMPORTED_MODULE_0__.jA}/${categoryId}`,{json:body}),deleteCategory=categoryId=>_fetch__WEBPACK_IMPORTED_MODULE_1__.d.delete(`${constants_apis_url__WEBPACK_IMPORTED_MODULE_0__.jA}/${categoryId}`)},"./src/apis/fetch.ts":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{d:()=>fetch_http});var distribution=__webpack_require__("./node_modules/ky/distribution/index.js"),HttpError=__webpack_require__("./src/utils/apis/HttpError.ts"),url=__webpack_require__("./src/constants/apis/url.ts");const fetch_http=distribution.ZP.create({hooks:{beforeError:[error=>{const{response,request,options}=error;return(0,HttpError.S)(response,request,options)}],beforeRequest:[request=>{request.headers.set("Authorization",`Bearer ${localStorage.getItem("accessToken")??""}`)}],afterResponse:[async(request,options,response)=>{if(401===response.status){const{error}=await response.json();if(4011===error.code){const newAccessToken=(await fetch_http.post(`${url.qW}/token/refresh`).json()).accessToken;return localStorage.setItem("accessToken",newAccessToken),request.headers.set("Authorization",`Bearer ${newAccessToken}`),fetch_http(request)}}}]}})},"./src/components/@common/Input/Input.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{J:()=>InputVariant,Z:()=>__WEBPACK_DEFAULT_EXPORT__});var react__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./node_modules/react/index.js"),styled_components__WEBPACK_IMPORTED_MODULE_2__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./node_modules/react/jsx-runtime.js");const InputVariant=["outline","filled","unstyled","underlined"],Input=({size="medium",labelText,supportingText,variant="outline",isError=!1,...rest},ref)=>{const inputId=(0,react__WEBPACK_IMPORTED_MODULE_0__.useId)();return(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsxs)(S.InputContainer,{children:[labelText&&(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsx)(S.Label,{htmlFor:inputId,$required:rest.required,$variant:variant,children:labelText}),(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsx)(S.Input,{id:inputId,ref,$size:size,$variant:variant,$isError:isError,...rest}),supportingText&&(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsx)(S.SupportingText,{$isError:isError,children:supportingText})]})};Input.displayName="Input";const __WEBPACK_DEFAULT_EXPORT__=(0,react__WEBPACK_IMPORTED_MODULE_0__.forwardRef)(Input),S={InputContainer:styled_components__WEBPACK_IMPORTED_MODULE_2__.zo.div`
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
  `};try{Category.displayName="Category",Category.__docgenInfo={description:"",displayName:"Category",props:{categoryId:{defaultValue:null,description:"",name:"categoryId",required:!0,type:{name:"number"}},categoryName:{defaultValue:null,description:"",name:"categoryName",required:!0,type:{name:"string"}},isDefaultCategory:{defaultValue:null,description:"",name:"isDefaultCategory",required:!0,type:{name:"boolean"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/Category/Category/Category.tsx#Category"]={docgenInfo:Category.__docgenInfo,name:"Category",path:"src/components/Category/Category/Category.tsx#Category"})}catch(__react_docgen_typescript_loader_error){}},"./src/components/Category/useCategoryMutation.ts":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{Q:()=>useCategoryMutation});var _tanstack_react_query__WEBPACK_IMPORTED_MODULE_2__=__webpack_require__("./node_modules/@tanstack/react-query/build/lib/QueryClientProvider.mjs"),_tanstack_react_query__WEBPACK_IMPORTED_MODULE_3__=__webpack_require__("./node_modules/@tanstack/react-query/build/lib/useMutation.mjs"),apis_category__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./src/apis/category.ts"),hooks_common_useToast__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./src/hooks/@common/useToast.ts"),utils_error__WEBPACK_IMPORTED_MODULE_4__=__webpack_require__("./src/utils/error.ts");const useCategoryMutation=onCategoryAdded=>{const queryClient=(0,_tanstack_react_query__WEBPACK_IMPORTED_MODULE_2__.NL)(),toast=(0,hooks_common_useToast__WEBPACK_IMPORTED_MODULE_1__.p)(),{mutate:addCategory}=(0,_tanstack_react_query__WEBPACK_IMPORTED_MODULE_3__.D)(apis_category__WEBPACK_IMPORTED_MODULE_0__.i8,{onSuccess:async()=>{await queryClient.invalidateQueries(["categories"]),setTimeout((()=>{onCategoryAdded?.()}))},onError:error=>{toast.show({type:"error",message:(0,utils_error__WEBPACK_IMPORTED_MODULE_4__.e)(error)})}}),{mutate:updateCategoryTitle}=(0,_tanstack_react_query__WEBPACK_IMPORTED_MODULE_3__.D)(apis_category__WEBPACK_IMPORTED_MODULE_0__.kQ,{onSuccess:()=>{queryClient.invalidateQueries(["categories"]),queryClient.invalidateQueries(["detailWritings"])}}),{mutate:updateCategoryOrder}=(0,_tanstack_react_query__WEBPACK_IMPORTED_MODULE_3__.D)(apis_category__WEBPACK_IMPORTED_MODULE_0__.K8,{onSuccess:()=>{queryClient.invalidateQueries(["categories"])}}),{mutate:deleteCategory}=(0,_tanstack_react_query__WEBPACK_IMPORTED_MODULE_3__.D)(apis_category__WEBPACK_IMPORTED_MODULE_0__.uu,{onSuccess:()=>{queryClient.invalidateQueries(["categories"]),queryClient.invalidateQueries(["writingsInCategory"])}});return{addCategory,updateCategoryTitle,updateCategoryOrder,deleteCategory}}},"./src/components/DeleteButton/DeleteButton.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{Z:()=>__WEBPACK_DEFAULT_EXPORT__});var assets_icons__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./src/assets/icons/index.ts"),styled_components__WEBPACK_IMPORTED_MODULE_2__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./node_modules/react/jsx-runtime.js");const DeleteButton=({...rest})=>(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsx)(S.Button,{...rest,children:(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsx)(assets_icons__WEBPACK_IMPORTED_MODULE_0__.Ms,{width:12,height:12,"aria-label":"휴지통 아이콘"})});DeleteButton.displayName="DeleteButton";const __WEBPACK_DEFAULT_EXPORT__=DeleteButton,S={Button:styled_components__WEBPACK_IMPORTED_MODULE_2__.zo.button`
    display: flex;
    justify-content: center;
    align-items: center;
    width: 2rem;
    height: 2.4rem;
    border-radius: 4px;

    &:hover {
      background-color: ${({theme})=>theme.color.gray5};
    }
  `};try{DeleteButton.displayName="DeleteButton",DeleteButton.__docgenInfo={description:"",displayName:"DeleteButton",props:{}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/DeleteButton/DeleteButton.tsx#DeleteButton"]={docgenInfo:DeleteButton.__docgenInfo,name:"DeleteButton",path:"src/components/DeleteButton/DeleteButton.tsx#DeleteButton"})}catch(__react_docgen_typescript_loader_error){}},"./src/hooks/@common/useUncontrolledInput.ts":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{Z:()=>__WEBPACK_DEFAULT_EXPORT__});var react__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./node_modules/react/index.js");const __WEBPACK_DEFAULT_EXPORT__=()=>{const[isError,setIsError]=(0,react__WEBPACK_IMPORTED_MODULE_0__.useState)(!1),[isInputOpen,setIsInputOpen]=(0,react__WEBPACK_IMPORTED_MODULE_0__.useState)(!1),inputRef=(0,react__WEBPACK_IMPORTED_MODULE_0__.useRef)(null);(0,react__WEBPACK_IMPORTED_MODULE_0__.useEffect)((()=>{inputRef.current&&inputRef.current.focus()}),[isInputOpen]);const resetInput=()=>{setIsError(!1),setIsInputOpen(!1)};return{inputRef,escapeInput:e=>{"Escape"===e.key&&resetInput()},isInputOpen,openInput:()=>setIsInputOpen(!0),resetInput,isError,setIsError}}},"./src/styles/storybook.ts":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{CA:()=>StoryItemContainer,VP:()=>StoryItemTitle,y1:()=>StoryContainer});var styled_components__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js");const StoryContainer=styled_components__WEBPACK_IMPORTED_MODULE_0__.zo.div`
  display: flex;
  flex-direction: column;
  width: 300px;
  gap: 28px;
`,StoryItemContainer=styled_components__WEBPACK_IMPORTED_MODULE_0__.zo.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 12px;
`,StoryItemTitle=(styled_components__WEBPACK_IMPORTED_MODULE_0__.zo.div`
  display: flex;
  flex-direction: row;
  gap: 12px;
`,styled_components__WEBPACK_IMPORTED_MODULE_0__.zo.h3`
  color: ${({theme})=>theme.color.gray9};
  font-size: 12px;
  font-weight: 400;
  text-transform: uppercase;
`)},"./src/utils/error.ts":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{e:()=>getErrorMessage});const getErrorMessage=error=>{if(error instanceof Error)return error.message}},"./src/utils/validators.ts":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{Z:()=>validateCategoryName,d:()=>validateWritingTitle});const validateCategoryName=categoryName=>{if(!(0<categoryName.length&&categoryName.length<=30))throw new Error("카테고리 이름은 1자 이상 30자 이하로 입력해주세요.")},validateWritingTitle=writingTitle=>{if(!(0<writingTitle.length&&writingTitle.length<=255))throw new Error("글 제목은 1자 이상 255자 이하로 입력해주세요.")}}}]);
//# sourceMappingURL=components-Category-Category-Category-stories.8e53d599.iframe.bundle.js.map