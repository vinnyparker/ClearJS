Ext.namespace("AM.direct.config");
Ext.namespace("AM.direct.action");
AM.direct.config.PROVIDER_BASE_URL=window.location.protocol+"//"+window.location.host+"/"+(window.location.pathname.split("/").length>2?window.location.pathname.split("/")[1]+"/":"")+"djn/directprovider";
AM.direct.config.POLLING_URLS={};
AM.direct.config.REMOTING_API={url:AM.direct.config.PROVIDER_BASE_URL,type:"remoting",namespace:AM.direct.action,actions:{UserAction:[{name:"loadUsers_insertItems",len:1,formHandler:false},{name:"loadUsers_deleteItems",len:1,formHandler:false},{name:"loadUsers",len:2,formHandler:false},{name:"loadUsers_updateItems",len:1,formHandler:false},{name:"loadUsers_sync",len:1,formHandler:false}],TicketAction:[{name:"getTickets_sync",len:1,formHandler:false},{name:"getTickets_insertItems",len:1,formHandler:false},{name:"getTickets_updateItems",len:1,formHandler:false},{name:"getTickets_deleteItems",len:1,formHandler:false},{name:"getTickets",len:1,formHandler:false}]}};