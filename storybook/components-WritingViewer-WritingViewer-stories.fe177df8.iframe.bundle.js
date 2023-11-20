"use strict";(self.webpackChunkfrontend=self.webpackChunkfrontend||[]).push([[911],{"./src/components/WritingViewer/WritingViewer.stories.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.r(__webpack_exports__),__webpack_require__.d(__webpack_exports__,{Failure:()=>Failure,Success:()=>Success,__namedExportsOrder:()=>__namedExportsOrder,default:()=>WritingViewer_stories});var storybook=__webpack_require__("./src/styles/storybook.ts"),purify=__webpack_require__("./node_modules/dompurify/dist/purify.js"),purify_default=__webpack_require__.n(purify),styled_components_browser_esm=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),writings=__webpack_require__("./src/apis/writings.ts"),Divider=__webpack_require__("./src/components/@common/Divider/Divider.tsx"),Spinner=__webpack_require__("./src/components/@common/Spinner/Spinner.tsx"),useQuery=__webpack_require__("./node_modules/@tanstack/react-query/build/lib/useQuery.mjs"),QueryClientProvider=__webpack_require__("./node_modules/@tanstack/react-query/build/lib/QueryClientProvider.mjs"),useMutation=__webpack_require__("./node_modules/@tanstack/react-query/build/lib/useMutation.mjs"),icons=__webpack_require__("./src/assets/icons/index.ts"),react=__webpack_require__("./node_modules/react/index.js"),utils_error=__webpack_require__("./src/utils/error.ts"),useToast=__webpack_require__("./src/hooks/@common/useToast.ts"),validators=__webpack_require__("./src/utils/validators.ts");const _common_useControlledInput=(defaultValue="")=>{const[value,setValue]=(0,react.useState)(defaultValue),[isError,setIsError]=(0,react.useState)(!1),[isInputOpen,setIsInputOpen]=(0,react.useState)(!1),inputRef=(0,react.useRef)(null);(0,react.useEffect)((()=>{inputRef.current&&inputRef.current.focus()}),[isInputOpen]);const resetInput=()=>{setIsError(!1),setIsInputOpen(!1)};return{value,setValue,inputRef,escapeInput:e=>{"Escape"===e.key&&resetInput()},isInputOpen,openInput:()=>setIsInputOpen(!0),resetInput,isError,setIsError}};var jsx_runtime=__webpack_require__("./node_modules/react/jsx-runtime.js");const WritingTitle=({writingId,categoryId,title,canEditTitle=!0})=>{const{value:inputTitle,setValue:setInputTitle,inputRef,escapeInput:escapeRename,isInputOpen,openInput,resetInput}=_common_useControlledInput(title),myRef=(0,react.useRef)(null),queryClient=(0,QueryClientProvider.NL)(),toast=(0,useToast.p)(),{mutate:updateWritingTitle}=(0,useMutation.D)(writings.Yh,{onMutate:async({writingId,body:{title}})=>{await queryClient.cancelQueries(["writings",writingId]),await queryClient.cancelQueries(["writingsInCategory",categoryId]);const previousWritings=queryClient.getQueryData(["writings",writingId]),previousWritingsInCategory=queryClient.getQueryData(["writingsInCategory",categoryId]);return previousWritings&&queryClient.setQueryData(["writings",writingId],(old=>({...old,title}))),previousWritingsInCategory&&queryClient.setQueryData(["writingsInCategory",categoryId],(old=>({...old,writings:old.writings.map((writing=>writing.id===writingId?{id:writing.id,title}:writing))}))),{previousWritings,previousWritingsInCategory}},onError:(error,_,context)=>{setInputTitle(context?.previousWritings?.title||""),queryClient.setQueryData(["writings",writingId],context?.previousWritings),queryClient.setQueryData(["writingsInCategory",categoryId],context?.previousWritingsInCategory),toast.show({type:"error",message:"글 제목 수정에 실패했습니다."})},onSettled:()=>{queryClient.invalidateQueries(["writings",writingId]),queryClient.invalidateQueries(["writingsInCategory",categoryId])}});return(0,react.useEffect)((()=>{myRef.current?.focus()}),[writingId]),(0,jsx_runtime.jsx)(S.TitleWrapper,{children:isInputOpen?(0,jsx_runtime.jsx)(S.Input,{type:"text",placeholder:"새 제목을 입력해주세요",defaultValue:title,value:inputTitle,ref:inputRef,onChange:e=>setInputTitle(e.target.value),onBlur:resetInput,onKeyDown:escapeRename,onKeyUp:e=>{try{if("Enter"!==e.key)return;const writingTitle=e.currentTarget.value.trim();(0,validators.d)(writingTitle),resetInput(),updateWritingTitle({writingId,body:{title:writingTitle}})}catch(error){toast.show({type:"error",message:(0,utils_error.e)(error)})}}}):(0,jsx_runtime.jsxs)(jsx_runtime.Fragment,{children:[(0,jsx_runtime.jsx)(S.Title,{ref:myRef,tabIndex:0,children:inputTitle}),canEditTitle&&(0,jsx_runtime.jsx)(S.Button,{"aria-label":"글 제목 수정",onClick:openInput,children:(0,jsx_runtime.jsx)(icons.vd,{width:20,height:20})})]})})};WritingTitle.displayName="WritingTitle";const WritingTitle_WritingTitle=WritingTitle,S={TitleWrapper:styled_components_browser_esm.zo.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-bottom: 2rem;
    gap: 0.3rem;
  `,Title:styled_components_browser_esm.zo.h1`
    font-size: 4rem;
    padding: 0.1rem;
  `,Button:styled_components_browser_esm.zo.button`
    display: flex;
    justify-content: center;
    align-items: center;
    flex-shrink: 0;
    border-radius: 12px;
    padding: 1rem;
    background-color: ${({theme})=>theme.color.gray4};

    &:hover {
      background-color: ${({theme})=>theme.color.gray5};
    }
  `,Input:styled_components_browser_esm.zo.input`
    font-size: 4rem;
    font-weight: 700;
    width: 100%;
    ${({theme})=>styled_components_browser_esm.iv`
      border: 1px solid ${theme.color.gray1};
      outline: 1px solid ${theme.color.gray1};
    `}
    ${({disabled})=>styled_components_browser_esm.iv`
      background-color: ${disabled?"initial":"desiredColor"};
      color: ${disabled?"initial":"desiredColor"};
    `}
  `};try{WritingTitle.displayName="WritingTitle",WritingTitle.__docgenInfo={description:"",displayName:"WritingTitle",props:{writingId:{defaultValue:null,description:"",name:"writingId",required:!0,type:{name:"number"}},categoryId:{defaultValue:null,description:"",name:"categoryId",required:!0,type:{name:"number"}},title:{defaultValue:null,description:"",name:"title",required:!0,type:{name:"string"}},canEditTitle:{defaultValue:{value:"true"},description:"",name:"canEditTitle",required:!1,type:{name:"boolean"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/WritingViewer/WritingTitle/WritingTitle.tsx#WritingTitle"]={docgenInfo:WritingTitle.__docgenInfo,name:"WritingTitle",path:"src/components/WritingViewer/WritingTitle/WritingTitle.tsx#WritingTitle"})}catch(__react_docgen_typescript_loader_error){}var prism=__webpack_require__("./node_modules/prismjs/prism.js"),prism_default=__webpack_require__.n(prism);__webpack_require__("./node_modules/prismjs/themes/prism.css"),__webpack_require__("./node_modules/prismjs/plugins/autoloader/prism-autoloader.js");prism_default().plugins.autoloader.languages_path="/prismjs/components/";const _common_useCodeHighlight=htmlDOMString=>{(0,react.useEffect)((()=>{if(!htmlDOMString)return;prism_default().highlightAll()}),[htmlDOMString])};var style=__webpack_require__("./src/constants/style.ts");const WritingViewer=({writingId,categoryId,isDeletedWriting})=>{const{data,isLoading}=(0,useQuery.a)(["writings",writingId],(()=>(0,writings.Jl)(writingId)));return _common_useCodeHighlight(data?.content),isLoading?(0,jsx_runtime.jsxs)(WritingViewer_S.LoadingContainer,{children:[(0,jsx_runtime.jsx)(Spinner.Z,{size:60,thickness:4}),(0,jsx_runtime.jsx)("h1",{children:"글을 불러오는 중입니다 ..."})]}):(0,jsx_runtime.jsxs)(WritingViewer_S.WritingViewerContainer,{children:[(0,jsx_runtime.jsx)(WritingTitle_WritingTitle,{categoryId,writingId,title:data?.title??"",canEditTitle:!isDeletedWriting}),(0,jsx_runtime.jsx)(Divider.Z,{}),(0,jsx_runtime.jsx)(WritingViewer_S.ContentWrapper,{tabIndex:0,dangerouslySetInnerHTML:{__html:purify_default().sanitize(data?.content??"글 내용이 없습니다")}})]})};WritingViewer.displayName="WritingViewer";const WritingViewer_WritingViewer=WritingViewer,generateResponsiveStyle_writingViewerContainer=styled_components_browser_esm.iv`
    @media (max-width: ${style.d.tablet}) {
      padding: 6rem;
    }

    @media (max-width: ${style.d.mobileLarge}) {
      padding: 5rem;
    }

    @media (max-width: ${style.d.mobileMedium}) {
      padding: 3rem;
    }
  `,WritingViewer_S={WritingViewerContainer:styled_components_browser_esm.zo.section`
    ${generateResponsiveStyle_writingViewerContainer}

    padding: 8rem;
    width: 100%;
    overflow-wrap: break-word;
    background-color: ${({theme})=>theme.color.gray1};
  `,LoadingContainer:styled_components_browser_esm.zo.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 2rem;
    max-width: 100%;
    height: 100%;
  `,ContentWrapper:styled_components_browser_esm.zo.section`
    padding: 1.6rem 0;
    font-size: 1.6rem;

    h1 {
      padding: 3.4rem 0 1.7rem;
      font-size: 3.4rem;
    }

    h2 {
      padding: 2.8rem 0 1.4rem;
      font-size: 2.8rem;
    }

    h3 {
      padding: 2.2rem 0 1.1rem;
      font-size: 2.2rem;
    }

    h4 {
      padding: 1.6rem 0 0.8rem;
      font-size: 1.6rem;
    }

    h5 {
      padding: 1.3rem 0 0.65rem;
      font-size: 1.3rem;
    }

    h6 {
      padding: 1rem 0 0.5rem;
      font-size: 1rem;
    }

    p {
      padding: 1rem 0;
      font-size: 1.6rem;
      line-height: 2.3rem;
    }

    blockquote {
      padding: 1rem 2rem;
      margin: 1.6rem 0;
      border-left: 4px solid ${({theme})=>theme.color.gray8};
      background-color: ${({theme})=>theme.color.gray2};
      color: ${({theme})=>theme.color.gray9};
      line-height: 2.4rem;
    }

    ol,
    ul {
      padding-left: 2rem;
    }

    ul > li {
      list-style: disc;
    }

    ol > li {
      list-style: decimal;
    }

    li {
      padding: 0.5rem 0;
    }

    a {
      color: #0968da;
      text-decoration: underline;
      &:visited {
        color: #0968da;
      }
    }

    code {
      padding: 0.2rem 0.4rem;
      margin: 0.1rem;
      border: none solid #eee;
      border-radius: 4px;
      background-color: ${({theme})=>theme.color.gray4};
      color: #d71919;
    }

    pre > code {
      color: inherit;
      background-color: transparent;
    }

    img {
      max-width: 100%;
      height: auto;
    }

    strong {
      font-weight: bold;
    }

    em {
      font-style: italic;
    }
  `};try{WritingViewer.displayName="WritingViewer",WritingViewer.__docgenInfo={description:"",displayName:"WritingViewer",props:{writingId:{defaultValue:null,description:"",name:"writingId",required:!0,type:{name:"number"}},categoryId:{defaultValue:null,description:"",name:"categoryId",required:!0,type:{name:"number"}},isDeletedWriting:{defaultValue:null,description:"",name:"isDeletedWriting",required:!1,type:{name:"boolean"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/WritingViewer/WritingViewer.tsx#WritingViewer"]={docgenInfo:WritingViewer.__docgenInfo,name:"WritingViewer",path:"src/components/WritingViewer/WritingViewer.tsx#WritingViewer"})}catch(__react_docgen_typescript_loader_error){}const WritingViewer_stories={title:"writing/WritingViewer",component:WritingViewer_WritingViewer,args:{writingId:200,categoryId:1},argTypes:{writingId:{description:"`writingId`에 해당하는 글을 서버에서 받아옵니다"}}},Success={render:({writingId,categoryId})=>(0,jsx_runtime.jsxs)(jsx_runtime.Fragment,{children:[(0,jsx_runtime.jsx)(storybook.VP,{children:"글 가져오기 성공"}),(0,jsx_runtime.jsx)(WritingViewer_WritingViewer,{categoryId,writingId})]})},Failure={render:()=>(0,jsx_runtime.jsx)(jsx_runtime.Fragment,{children:(0,jsx_runtime.jsxs)(jsx_runtime.Fragment,{children:[(0,jsx_runtime.jsx)(storybook.VP,{children:"글 가져오기 실패"}),(0,jsx_runtime.jsx)(WritingViewer_WritingViewer,{categoryId:1,writingId:404})]})})};Success.parameters={...Success.parameters,docs:{...Success.parameters?.docs,source:{originalSource:"{\n  render: ({\n    writingId,\n    categoryId\n  }) => {\n    return <>\n        <StoryItemTitle>글 가져오기 성공</StoryItemTitle>\n        <WritingViewer categoryId={categoryId} writingId={writingId}></WritingViewer>\n      </>;\n  }\n}",...Success.parameters?.docs?.source}}},Failure.parameters={...Failure.parameters,docs:{...Failure.parameters?.docs,source:{originalSource:"{\n  render: () => {\n    return <>\n        <>\n          <StoryItemTitle>글 가져오기 실패</StoryItemTitle>\n          <WritingViewer categoryId={1} writingId={404}></WritingViewer>\n        </>\n      </>;\n  }\n}",...Failure.parameters?.docs?.source}}};const __namedExportsOrder=["Success","Failure"]},"./src/apis/fetch.ts":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{d:()=>fetch_http});var distribution=__webpack_require__("./node_modules/ky/distribution/index.js"),HttpError=__webpack_require__("./src/utils/apis/HttpError.ts"),url=__webpack_require__("./src/constants/apis/url.ts");const fetch_http=distribution.ZP.create({hooks:{beforeError:[error=>{const{response,request,options}=error;return(0,HttpError.S)(response,request,options)}],beforeRequest:[request=>{request.headers.set("Authorization",`Bearer ${localStorage.getItem("accessToken")??""}`)}],afterResponse:[async(request,options,response)=>{if(401===response.status){const{error}=await response.json();if(4011===error.code){const newAccessToken=(await fetch_http.post(`${url.qW}/token/refresh`).json()).accessToken;return localStorage.setItem("accessToken",newAccessToken),request.headers.set("Authorization",`Bearer ${newAccessToken}`),fetch_http(request)}}}]}})},"./src/apis/writings.ts":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{I3:()=>publishWritingToMedium,Jl:()=>getWriting,Yh:()=>updateWritingTitle,_H:()=>getWritingProperties,a0:()=>updateWritingOrder,aU:()=>publishWritingToTistory});var constants_apis_url__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./src/constants/apis/url.ts"),_fetch__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./src/apis/fetch.ts");const getWriting=writingId=>_fetch__WEBPACK_IMPORTED_MODULE_1__.d.get(`${constants_apis_url__WEBPACK_IMPORTED_MODULE_0__.lw}/${writingId}`).json(),getWritingProperties=writingId=>_fetch__WEBPACK_IMPORTED_MODULE_1__.d.get(`${constants_apis_url__WEBPACK_IMPORTED_MODULE_0__.lw}/${writingId}/properties`).json(),publishWritingToTistory=({writingId,body})=>_fetch__WEBPACK_IMPORTED_MODULE_1__.d.post(`${constants_apis_url__WEBPACK_IMPORTED_MODULE_0__.lw}/${writingId}/publish/tistory`,{json:body}),publishWritingToMedium=({writingId,body})=>_fetch__WEBPACK_IMPORTED_MODULE_1__.d.post(`${constants_apis_url__WEBPACK_IMPORTED_MODULE_0__.lw}/${writingId}/publish/medium`,{json:body}),updateWritingTitle=({writingId,body})=>_fetch__WEBPACK_IMPORTED_MODULE_1__.d.patch(`${constants_apis_url__WEBPACK_IMPORTED_MODULE_0__.lw}/${writingId}`,{json:body}),updateWritingOrder=({writingId,body})=>_fetch__WEBPACK_IMPORTED_MODULE_1__.d.patch(`${constants_apis_url__WEBPACK_IMPORTED_MODULE_0__.lw}/${writingId}`,{json:body})},"./src/components/@common/Divider/Divider.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{Z:()=>__WEBPACK_DEFAULT_EXPORT__});var styled_components__WEBPACK_IMPORTED_MODULE_2__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),styles_theme__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./src/styles/theme.ts"),react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./node_modules/react/jsx-runtime.js");const Divider=({length="100%",direction="horizontal"})=>(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsx)(S.Divider,{$length:length,$direction:direction});Divider.displayName="Divider";const __WEBPACK_DEFAULT_EXPORT__=Divider,S={Divider:styled_components__WEBPACK_IMPORTED_MODULE_2__.zo.div`
    ${({$direction,$length})=>{return direction=$direction,length=$length,{horizontal:styled_components__WEBPACK_IMPORTED_MODULE_2__.iv`
      border-bottom: 1px solid ${styles_theme__WEBPACK_IMPORTED_MODULE_0__.r.color.gray5};
      width: ${length};
    `,vertical:styled_components__WEBPACK_IMPORTED_MODULE_2__.iv`
      border-left: 1px solid ${styles_theme__WEBPACK_IMPORTED_MODULE_0__.r.color.gray5};
      height: ${length};
    `}[direction];var direction,length}};
  `};try{Divider.displayName="Divider",Divider.__docgenInfo={description:"",displayName:"Divider",props:{length:{defaultValue:{value:"100%"},description:"",name:"length",required:!1,type:{name:"string"}},direction:{defaultValue:{value:"horizontal"},description:"",name:"direction",required:!1,type:{name:"enum",value:[{value:'"horizontal"'},{value:'"vertical"'}]}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/@common/Divider/Divider.tsx#Divider"]={docgenInfo:Divider.__docgenInfo,name:"Divider",path:"src/components/@common/Divider/Divider.tsx#Divider"})}catch(__react_docgen_typescript_loader_error){}},"./src/components/@common/Spinner/Spinner.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{Z:()=>__WEBPACK_DEFAULT_EXPORT__});var styled_components__WEBPACK_IMPORTED_MODULE_3__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),styles_animation__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./src/styles/animation.ts"),styles_theme__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./src/styles/theme.ts"),react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__=__webpack_require__("./node_modules/react/jsx-runtime.js");const Spinner=({size=30,thickness=4,duration=1,backgroundColor=styles_theme__WEBPACK_IMPORTED_MODULE_1__.r.color.gray4,barColor=styles_theme__WEBPACK_IMPORTED_MODULE_1__.r.color.primary})=>(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)(S.Spinner,{$size:size,$thickness:thickness,$duration:duration,$backgroundColor:backgroundColor,$barColor:barColor});Spinner.displayName="Spinner";const __WEBPACK_DEFAULT_EXPORT__=Spinner,S={Spinner:styled_components__WEBPACK_IMPORTED_MODULE_3__.zo.div`
    ${({$size,$thickness,$backgroundColor,$barColor,$duration})=>styled_components__WEBPACK_IMPORTED_MODULE_3__.iv`
        display: inline-block;

        width: ${$size}px;
        height: ${$size}px;

        border: ${$thickness}px solid ${$backgroundColor};
        border-bottom-color: ${$barColor};
        border-radius: 50%;

        animation: ${styles_animation__WEBPACK_IMPORTED_MODULE_0__.Wn} ${$duration}s linear infinite;
      `}
  `};try{Spinner.displayName="Spinner",Spinner.__docgenInfo={description:"",displayName:"Spinner",props:{size:{defaultValue:{value:"30"},description:"",name:"size",required:!1,type:{name:"number"}},thickness:{defaultValue:{value:"4"},description:"",name:"thickness",required:!1,type:{name:"number"}},duration:{defaultValue:{value:"1"},description:"",name:"duration",required:!1,type:{name:"number"}},backgroundColor:{defaultValue:{value:"#f0f0f0"},description:"",name:"backgroundColor",required:!1,type:{name:"string"}},barColor:{defaultValue:{value:"#405178"},description:"",name:"barColor",required:!1,type:{name:"string"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/@common/Spinner/Spinner.tsx#Spinner"]={docgenInfo:Spinner.__docgenInfo,name:"Spinner",path:"src/components/@common/Spinner/Spinner.tsx#Spinner"})}catch(__react_docgen_typescript_loader_error){}},"./src/styles/storybook.ts":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{CA:()=>StoryItemContainer,VP:()=>StoryItemTitle,y1:()=>StoryContainer});var styled_components__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js");const StoryContainer=styled_components__WEBPACK_IMPORTED_MODULE_0__.zo.div`
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
//# sourceMappingURL=components-WritingViewer-WritingViewer-stories.fe177df8.iframe.bundle.js.map