import{d as f,b as o,o as a,c,w as t,e as n,i as p,k as x,f as v,g,F as $,h as I,J as C,A as w,u as m,K as B,G as E,H as N,_ as R,R as b,L as k}from"./index.2d3701b6.js";const S=f({__name:"MenuItem",props:{menu:{type:Object,required:!0}},setup(e){return(u,d)=>{var l;const s=o("svg-icon"),h=o("menu-item",!0),_=o("el-sub-menu"),r=o("el-menu-item");return((l=e.menu.children)==null?void 0:l.length)>0?(a(),c(_,{key:e.menu.path,index:e.menu.path},{title:t(()=>[n(s,{icon:e.menu.meta.icon},null,8,["icon"]),p("span",null,x(e.menu.meta.title),1)]),default:t(()=>[(a(!0),v($,null,g(e.menu.children,i=>(a(),c(h,{key:i.path,menu:i},null,8,["menu"]))),128))]),_:1},8,["index"])):(a(),c(r,{key:e.menu.path,index:e.menu.path},{title:t(()=>[I(x(e.menu.meta.title),1)]),default:t(()=>[n(s,{icon:e.menu.meta.icon},null,8,["icon"])]),_:1},8,["index"]))}}}),V=f({__name:"Menu",setup(e){const u=C(),d=w(()=>{const{path:s}=u;return s});return(s,h)=>{const _=o("el-menu"),r=o("el-scrollbar");return a(),c(r,null,{default:t(()=>[n(_,{"default-active":m(d),"background-color":"transparent","collapse-transition":!1,mode:"vertical",router:""},{default:t(()=>[(a(!0),v($,null,g(m(B),l=>(a(),c(S,{key:l.path,menu:l},null,8,["menu"]))),128))]),_:1},8,["default-active"])]),_:1})}}}),D=f({__name:"index",setup(e){return(u,d)=>{const s=o("el-aside");return a(),c(s,{class:"layout-sidebar"},{default:t(()=>[n(V)]),_:1})}}}),F=e=>(E("data-v-11403d2e"),e=e(),N(),e),q={class:"navbar-container"},A=F(()=>p("div",{class:"navbar-left"},"\u4EE3\u7801\u751F\u6210\u5668\u5DE5\u4F5C\u53F0",-1)),H=F(()=>p("div",{class:"navbar-right"},null,-1)),L=[A,H],M=f({__name:"index",setup(e){return(u,d)=>(a(),v("div",q,L))}});const j=R(M,[["__scopeId","data-v-11403d2e"]]),G={class:"layout-card"},J={class:"layout-card"},O=f({__name:"index",setup(e){const u=C();return(d,s)=>{const h=o("el-header"),_=o("el-scrollbar"),r=o("el-main"),l=o("el-container");return m(u).query.isHidden?(a(),c(r,{key:1,class:"layout-main"},{default:t(()=>[n(_,{class:"layout-scrollbar"},{default:t(()=>[p("div",J,[n(m(b),null,{default:t(({Component:i,route:y})=>[(a(),c(k(i),{key:y.name}))]),_:1})])]),_:1})]),_:1})):(a(),c(l,{key:0,class:"layout-container"},{default:t(()=>[n(h,{class:"layout-header"},{default:t(()=>[n(j)]),_:1}),n(l,null,{default:t(()=>[n(D),n(r,{class:"layout-main"},{default:t(()=>[n(_,{class:"layout-scrollbar"},{default:t(()=>[p("div",G,[n(m(b),null,{default:t(({Component:i,route:y})=>[(a(),c(k(i),{key:y.name}))]),_:1})])]),_:1})]),_:1})]),_:1})]),_:1}))}}});export{O as default};
