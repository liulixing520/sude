(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdOrderItemDialogController', SdOrderItemDialogController);

    SdOrderItemDialogController.$inject = ['$timeout', '$scope', '$http','$filter','$stateParams', '$uibModalInstance', 'entity','sequence','itemInfos','stations', 'SdOrderItem','SequenceValue','Enumeration','SdItemInfo','SdStation'];

    function SdOrderItemDialogController ($timeout, $scope,$http,$filter, $stateParams, $uibModalInstance, entity,sequence,itemInfos,stations, SdOrderItem,SequenceValue,Enumeration,SdItemInfo,SdStation) {
        var vm = this;

        vm.sdOrderItem = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.sumOfItems = sumOfItems;
        vm.save = save;
        vm.enumerations = Enumeration.query();//保险公司类型
        vm.sdStations = stations;
        
        function sort() {
            var result = ['id,asc'];
            return result;
        }
        //暂用sequence来判断是否为编辑页面/如果为null是编辑、不为null则是新增
        if(sequence!=null){
        	vm.sdOrderItem.consignDate = $filter("date")(new Date(), "yyyy-MM-dd");
        	vm.sdOrderItem.id = sequence.seqId;
        	vm.sdOrderItem.orderStat = 'orderStat_1';//新运单状态为：新建状态
        }
        //保证货物栏有四条记录
    	while (itemInfos.length<4)
    	{
    		itemInfos.push({id:null});
    	}
    	vm.sdItemInfos = itemInfos;
    	
    	//合计
    	function sumOfItems(type) {
    		if(type == ''){
    	    	vm.itemNum = 0;vm.weight = 0;vm.volume = 0;vm.freight = 0;vm.kickBack = 0;vm.cod = 0;vm.deliveryExpense = 0;vm.claimingValue = 0;vm.premium = 0;    			
    		}else if(type == 'itemNum'){
				vm.itemNum = 0;  
			}else if(type == 'weight'){
				vm.weight = 0;  
			}else if(type == 'volume'){
				vm.volume = 0;  
			}else if(type == 'freight'){
				vm.freight = 0;  
			}else if(type == 'kickBack'){
				vm.kickBack = 0;  
			}else if(type == 'cod'){
				vm.cod = 0;  
			}else if(type == 'deliveryExpense'){
				vm.deliveryExpense = 0;  
			}else if(type == 'claimingValue'){
				vm.claimingValue = 0;  
			}else if(type == 'premium'){
				vm.premium = 0;  
			}
    		for(var i = vm.sdItemInfos.length; i--;){
    			var itemInfo = vm.sdItemInfos[i];
    			if(type == 'itemNum'){
    				vm.itemNum += itemInfo.itemNum ? itemInfo.itemNum : 0;  
    			}else if(type == 'weight'){
    				vm.weight += itemInfo.weight ? itemInfo.weight : 0;  
    			}else if(type == 'volume'){
    				vm.volume += itemInfo.volume ? itemInfo.volume : 0;  
    			}else if(type == 'freight'){
    				vm.freight += itemInfo.freight ? itemInfo.freight : 0;  
    			}else if(type == 'kickBack'){
    				vm.kickBack += itemInfo.kickBack ? itemInfo.kickBack : 0;  
    			}else if(type == 'cod'){
    				vm.cod += itemInfo.cod ? itemInfo.cod : 0;  
    			}else if(type == 'deliveryExpense'){
    				vm.deliveryExpense += itemInfo.deliveryExpense ? itemInfo.deliveryExpense : 0;  
    			}else if(type == 'claimingValue'){
    				vm.claimingValue += itemInfo.claimingValue ? itemInfo.claimingValue : 0;  
    			}else if(type == 'premium'){
    				vm.premium += itemInfo.premium ? itemInfo.premium : 0;  
    			}else if(type == ''){
    				vm.itemNum += itemInfo.itemNum ? itemInfo.itemNum : 0; vm.weight += itemInfo.weight ? itemInfo.weight : 0;vm.volume += itemInfo.volume ? itemInfo.volume : 0;vm.freight += itemInfo.freight ? itemInfo.freight : 0;
    				vm.kickBack += itemInfo.kickBack ? itemInfo.kickBack : 0;vm.cod += itemInfo.cod ? itemInfo.cod : 0;vm.deliveryExpense += itemInfo.deliveryExpense ? itemInfo.deliveryExpense : 0;vm.claimingValue += itemInfo.claimingValue ? itemInfo.claimingValue : 0;
    				vm.premium += itemInfo.premium ? itemInfo.premium : 0; 
    			}
    			vm.sdOrderItem.goodsNo = vm.sdOrderItem.id+"-"+vm.itemNum;
    		}
    	}
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
            sumOfItems('');
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (sequence == null) {
                SdOrderItem.update(vm.sdOrderItem, saveItemInfo, onSaveError);
            } else {
                SdOrderItem.save(vm.sdOrderItem, saveItemInfo, onSaveError);
            }
        }
        //保存货物明细
        function saveItemInfo(result){
        	for (var i = vm.sdItemInfos.length; i--;) {
      	       var itemInfo = vm.sdItemInfos[i];
      	       itemInfo.orderNo = result.id;
      	       if(itemInfo.tradName !==undefined && itemInfo.tradName !=="")
  	    	   if (itemInfo.id !== null) {
  	    		   SdItemInfo.update(itemInfo, onSaveSuccess, onSaveError);
  	    	   } else {
  	    		   SdItemInfo.save(itemInfo, onSaveSuccess, onSaveError);
  	    	   }
      	    }
        	
        	$scope.$emit('sudeApp:sdOrderItemUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveSuccess (result) {
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
        
        // 从后台搜索获取数据
        $scope.getCustomer = function(val) {
          return $http.get('/api/_search/searchSdCustomers', {
            params: {
              query: val,
              sensor: false
            }
          }).then(function(response){
            return response.data;
          });
        };
        //发货人补充信息
        $scope.setconsignerDetail = function ($item, $model) { 
        	vm.sdOrderItem.consignerName = $item.customerName;
        	vm.sdOrderItem.consignerId = $item.id;
        	vm.sdOrderItem.consignerAddress = $item.address;
        	vm.sdOrderItem.consignerMbPhone = $item.mobilePhone;
        	vm.sdOrderItem.consignerPhone = $item.phone;
        };
        //收货人补充信息
        $scope.setconsigneeDetail = function ($item, $model) { 
        	vm.sdOrderItem.consigneeName = $item.customerName;
        	vm.sdOrderItem.consigneeId = $item.id;
        	vm.sdOrderItem.consigneeAddress = $item.address;
        	vm.sdOrderItem.consigneeMbPhone = $item.mobilePhone;
        	vm.sdOrderItem.consigneePhone = $item.phone;
        };
        
        // 从后台搜索获取数据
        $scope.getStation = function(val) {
        	return $http.get('/api/_search/searchSdStation', {
        		params: {
        			query: val,
        			sensor: false
        		}
        	}).then(function(response){
        		return response.data;
        	});
        };
        //选中到达站
        $scope.setconStationDetail = function ($item, $model) { 
        	vm.sdOrderItem.toStation = $item.id;
        	vm.sdOrderItem.toStationName = $item.stationName;
        };
        
    }
})();
