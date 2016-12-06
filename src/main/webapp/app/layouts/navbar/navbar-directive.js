(function() {
    'use strict';

    angular
        .module('sudeApp')
        .directive('myCurrentTime', myCurrentTime);

    myCurrentTime.$inject = ['$timeout', 'dateFilter'];

    function myCurrentTime($timeout,dateFilter) {
        var directive = {
            restrict: 'A',
            link: linkFunc
        };

        return directive;
        

        function linkFunc(scope, element, attrs) {
        	var timeoutId; // timeoutId, so that we can cancel the time updates

    		// used to update the UI
    		function updateTime() {
    			var a = new Array("日", "一", "二", "三", "四", "五", "六");  
    			var date = new Date();
    			var week = date.getDay();  
    			var str = " 星期"+ a[week];  
    			element.text(dateFilter(date, 'yyyy-MM-dd HH:mm:ss')+"   "+str);
    		}

    		// watch the expression, and update the UI on change.
    		scope.$watch(attrs.myCurrentTime, function(value) {
    			updateTime();
    		});

    		// schedule update in one second
    		function updateLater() {
    			// save the timeoutId for canceling
    			timeoutId = $timeout(function() {
    				updateTime(); // update DOM
    				updateLater(); // schedule another update
    			}, 1000);
    		}

    		// listen on DOM destroy (removal) event, and cancel the next UI update
    		// to prevent updating time ofter the DOM element was removed.
    		element.bind('$destroy', function() {
    			$timeout.cancel(timeoutId);
    		});

    		updateLater(); // kick off the UI update process.
        }
    }
})();
