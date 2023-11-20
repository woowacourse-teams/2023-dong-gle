"use strict";(self.webpackChunkfrontend=self.webpackChunkfrontend||[]).push([[809],{"./src/components/@common/FileUploader/FileUploader.stories.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.r(__webpack_exports__),__webpack_require__.d(__webpack_exports__,{Playground:()=>Playground,__namedExportsOrder:()=>__namedExportsOrder,default:()=>FileUploader_stories});var react=__webpack_require__("./node_modules/react/index.js"),styled_components_browser_esm=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),utils_error=__webpack_require__("./src/utils/error.ts");var icons=__webpack_require__("./src/assets/icons/index.ts"),jsx_runtime=__webpack_require__("./node_modules/react/jsx-runtime.js");const FileUploader=({accept="*",width="30rem",height="10rem",onFileSelect})=>{const{onFileChange,openFinder,selectedFile}=((accept="*")=>{const[selectedFile,setSelectedFile]=(0,react.useState)(null),onFileChange=e=>{try{const target="dataTransfer"in e?e.dataTransfer:e.target;if(!target.files)return;const newFile=target.files[0];if(newFile.size>5242880)throw new Error("업로드 가능한 최대 용량은 5MB입니다.");const formData=new FormData;formData.append("file",newFile),setSelectedFile(formData)}catch(error){alert((0,utils_error.e)(error))}};return{selectedFile,openFinder:()=>{const fileInput=document.createElement("input");fileInput.type="file",fileInput.onchange=onFileChange,fileInput.accept=accept,fileInput.click()},onFileChange}})(accept),{dragRef,isDragging}=(({onFileChange})=>{const[isDragging,setIsDragging]=(0,react.useState)(!1),dragRef=(0,react.useRef)(null),onDragEnter=e=>{e.preventDefault(),e.stopPropagation(),setIsDragging(!0)},onDragLeave=e=>{e.preventDefault(),e.stopPropagation(),setIsDragging(!1)},onDragOver=e=>{e.preventDefault(),e.stopPropagation(),e.dataTransfer?.files&&setIsDragging(!0)},onDrop=e=>{e.preventDefault(),e.stopPropagation(),onFileChange(e),setIsDragging(!1)},initDragEvents=(0,react.useCallback)((()=>{dragRef.current&&(dragRef.current.addEventListener("dragenter",onDragEnter),dragRef.current.addEventListener("dragleave",onDragLeave),dragRef.current.addEventListener("dragover",onDragOver),dragRef.current.addEventListener("drop",onDrop))}),[onDragEnter,onDragLeave,onDragOver,onDrop]),resetDragEvents=(0,react.useCallback)((()=>{dragRef.current&&(dragRef.current.removeEventListener("dragenter",onDragEnter),dragRef.current.removeEventListener("dragleave",onDragLeave),dragRef.current.removeEventListener("dragover",onDragOver),dragRef.current.removeEventListener("drop",onDrop))}),[onDragEnter,onDragLeave,onDragOver,onDrop]);return(0,react.useEffect)((()=>(initDragEvents(),()=>resetDragEvents())),[initDragEvents,resetDragEvents]),{dragRef,isDragging}})({onFileChange});return(0,react.useEffect)((()=>{onFileSelect(selectedFile)}),[selectedFile]),(0,jsx_runtime.jsx)("button",{ref:dragRef,onClick:openFinder,children:(0,jsx_runtime.jsxs)(S.Description,{$isDragging:isDragging,$width:width,$height:height,"aria-label":"파일 업로드",children:[(0,jsx_runtime.jsx)(icons._c,{}),"드래그하거나 클릭해서 업로드"]})})};FileUploader.displayName="FileUploader";const FileUploader_FileUploader=FileUploader,S={Description:styled_components_browser_esm.zo.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 1rem;
    ${({$width,$height})=>styled_components_browser_esm.iv`
        width: ${$width};
        height: ${$height};
      `};
    border: 2px dashed ${({theme})=>theme.color.gray6};
    background-color: ${({theme})=>theme.color.gray4};
    font-size: 1.3rem;
    color: ${({theme})=>theme.color.gray7};
    transition: all 0.2s ease-in-out;

    ${({$isDragging,theme})=>$isDragging&&styled_components_browser_esm.iv`
          border: 2px dashed ${theme.color.primary};
          background-color: ${theme.color.gray5};
        `}
    &:hover {
      background-color: ${({theme})=>theme.color.gray5};
    }
  `,SpinnerWrapper:styled_components_browser_esm.zo.div`
    ${({$width,$height})=>styled_components_browser_esm.iv`
        width: ${$width};
        height: ${$height};
      `};

    display: flex;
    justify-content: center;
  `};try{FileUploader.displayName="FileUploader",FileUploader.__docgenInfo={description:"",displayName:"FileUploader",props:{accept:{defaultValue:{value:"*"},description:"",name:"accept",required:!1,type:{name:"string"}},width:{defaultValue:{value:"30rem"},description:"",name:"width",required:!1,type:{name:"string"}},height:{defaultValue:{value:"10rem"},description:"",name:"height",required:!1,type:{name:"string"}},onFileSelect:{defaultValue:null,description:"",name:"onFileSelect",required:!0,type:{name:"(file: FormData | null) => void"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/@common/FileUploader/FileUploader.tsx#FileUploader"]={docgenInfo:FileUploader.__docgenInfo,name:"FileUploader",path:"src/components/@common/FileUploader/FileUploader.tsx#FileUploader"})}catch(__react_docgen_typescript_loader_error){}const FileUploader_stories={title:"common/FileUploader",component:FileUploader_FileUploader},Playground={render:()=>(0,jsx_runtime.jsx)(FileUploader_FileUploader,{onFileSelect:file=>{file&&alert("파일이 선택되었습니다!")}})};Playground.parameters={...Playground.parameters,docs:{...Playground.parameters?.docs,source:{originalSource:"{\n  render: () => {\n    const onFileSelect = (file: FormData | null) => {\n      if (file) alert('파일이 선택되었습니다!');\n    };\n    return <FileUploader onFileSelect={onFileSelect} />;\n  }\n}",...Playground.parameters?.docs?.source}}};const __namedExportsOrder=["Playground"]},"./src/utils/error.ts":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{e:()=>getErrorMessage});const getErrorMessage=error=>{if(error instanceof Error)return error.message}}}]);
//# sourceMappingURL=components-common-FileUploader-FileUploader-stories.d65a7823.iframe.bundle.js.map