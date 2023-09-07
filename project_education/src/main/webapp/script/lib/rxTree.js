/*
 * 
 * LICENSE FREE BY RYU
 * 
 * (	CONVERT
 * 		ARRAY INTO TREE DATA	)
 *  FOR USING THIS, SORTING DATA BEFORE GET TREE DATA
 *  
 *  [
 *  {keyName : 'keyName', column1 : 'column1', column2 : 'column2', column3: 'column3},					<--
 *  {keyName : 'keyName1', column1 : 'column1', column2 : 'column2', column3: 'column3},					| 	-- typeA
 *  {keyName : 'keyName2', column1 : 'column1', column2 : 'column2', column3: 'column3},				<--
 *  {keyName : 'keyNameZZ', columnZZZ : 'columnZZZ', column2 : 'column2', column3: 'column3},			<--- *here*
 *  {keyName : 'keyName3', column1 : 'column1', column2 : 'column2', column3: 'column3},				<--\	-- type A 
 *  {keyName : 'keyName4', column1 : 'column1', column2 : 'column2', column3: 'column3},				<--\	-- type A
 *  ]
 *  
 *  INTO THIS
 *  
 *  [
 *  {keyName : 'keyName', column1 : 'column1', column2 : 'column2', column3: 'column3},					<--
 *  {keyName : 'keyName1', column1 : 'column1', column2 : 'column2', column3: 'column3},					|
 *  {keyName : 'keyName2', column1 : 'column1', column2 : 'column2', column3: 'column3},					|	-- typeA
 *  {keyName : 'keyName3', column1 : 'column1', column2 : 'column2', column3: 'column3},					|
 *  {keyName : 'keyName4', column1 : 'column1', column2 : 'column2', column3: 'column3},				<--
 *  {keyName : 'keyNameZZ', columnZZZ : 'columnZZZ', column2 : 'column2', column3: 'column3},			<--- *here*
 *  ]
 * 
 * 
 * DO SORT FIRST
 * IF NOT, IT WILL MAKE EMPTY WRONG TREE
 * 
 * ** 메뉴 레이어는 하위 컬럼에서 솔팅되어 데이터가 넘어옴으로 문제 없음.
 * ** data_div0_asc, data_div1_asc, data_div2_asc
 * ** 기관은 index 값으로 솔팅하고 있음
 * ** 기관은 설정저장 할 때 기존에 있던 항목을 truncate 하고 전부 다시 insert 하고 있음
 * ** 기관 백업 데이터는 postfix : _hist 에 추가 ( 설정저장을 누르면 데이터가 truncate 되기 전에 ~~~~~~_hist에 이전 데이터를 백업하는 방식
 */ 


(function(window){
    var guid = function() {
        function s4() {
            return ((1 + Math.random()) * 0x10000 | 0).toString(16).substring(1);
        }
        return s4() + s4() + '-' + s4() + '-' + s4() + '-' + s4() + '-' + s4() + s4() + s4();
    };
    var rxTreeNode = function(_name){
        var rxGuid = guid();
        return [{
            parentId : rxGuid,
            id : rxGuid,
            name : _name,
            children : []
        }]
    };
    var RxTree = function(_constructor){
        this.constructor = _constructor;
        this.maxDepth = 0;
        this.rxMap = new rxTreeNode(_constructor);
    };

    var RxTreeUtilHTML = function(_constructor){
        this.constructor = _constructor;
    };

    RxTree.prototype.createRightDataForm = function(_dataList, _option){
        var dataList = _dataList;
        var option =  _option;
        var hasExtraFix = false;
        if(!dataList){
            console.log("NO DATA");
            return null;
        }
        if(dataList.length === 0){
            console.log("NO DATA");
            return [];
        }
        if(!option){
            console.log("NO OPTION");
            return null;
        }
        if(!option['keyName']){
            console.log("NO keyName IN A OPTION");
            return null;
        }
        if(!option['prefix']){
            console.log("NO prefix IN A OPTION");
            return null;
        }
        if(option['extraFix']){
            hasExtraFix = true;
        }


//        var newMenuList = [];
        var eachDataList = [];
                
        for(var i = 0; i < dataList.length; i++){
            
            var rawName = dataList[i][option.keyName];
            var obKeys = Object.keys(dataList[i]);
            var tempArr = [];
            var tempStrChecker = [];
            for(var k = 0; k < obKeys.length; k++){
                var each = dataList[i][obKeys[k]];
                if(each !== null && each !== '-'){
                    if(obKeys[k].indexOf(option.prefix) !== -1){
                        if(!hasExtraFix){
                            var tempDdd = {name : each, rawName : rawName}; 
                            tempArr.push(tempDdd);
                        }else{
                            if(obKeys[k].indexOf(option.extraFix) === -1){
                                var tempDdd = {name : each, rawName : rawName}; 
                                tempArr.push(tempDdd);
                            }	
                        }
                        // maxDepth Count
                        if(this.maxDepth < k){
                            this.maxDepth = k;
                        }
                    }
                }

            }

            tempArr.push({name : rawName});
            eachDataList.push(tempArr);
        }
        // make tree data
        return eachDataList;
    };

    RxTree.prototype.findNodeFromOrigin = function(_originNode, _name){
        var orgin = _originNode;
        var name = _name;

        var findTarget = false;
        for(var i = 0; i < orgin.length; i++){
            if(orgin[i]['name'] === name){
                // found target
                findTarget = true;
                break;
                
            }else{
                if(orgin[i]['children'][0]){
                    findTarget = this.findNodeFromOrigin(orgin[i]['children'], name);
                }
            }
        }
        
        return findTarget;
    };

    RxTree.prototype.puttingDataToOrigin = function(_originBox, _arr, _str, _target_depth, _current_depth, _parent_guid){
        var originB = _originBox;
        var arr = _arr;
        var name = _str;
        var target_depth = _target_depth;
        var depth = _current_depth + 1;
        var current_guid = guid();
        var parent_guid = _parent_guid;
        
        
        if(target_depth === 1){
            //	includes checker
            var hasValue = false;
            for(var i = 0; i < originB.length; i++){
                if(originB[i]['name'] === name){
                    hasValue = true;
                    break;
                }
            }
            
            if(!hasValue){
                originB.push(
                        new function () {
                            return {
                                parentId : parent_guid,
                                id : current_guid,
                                name : name,
                                children : []
                            }
                        }
                    );				
            } 
        }else{
            
            if(target_depth === depth){
                originB.push(
                        new function () {
                            return {
                                parentId : parent_guid,
                                id : current_guid,
                                name : name,
                                children : []
                            }
                        }
                    );
            }else{
                var tempValue = arr[depth - 1]['name'];
                
                for(var i = 0; i < originB.length; i++){
                    var tempName = originB[i]['name'];
                    
                    if(tempName === tempValue){
                        current_guid = originB[i]['id'];
                        originB = originB[i]['children'];
                        break;
                    }
                }
                this.puttingDataToOrigin(originB, arr, name, target_depth, depth, current_guid);
            }
            
        }
    };

    RxTree.prototype.hasNodeInSameDepth = function(_depth, _name){
        var targetDepth = _depth;
        var name = _name;

        function findRecur(_tree, _currentDepth, _targetName){

            var findVal = false;
            for(var i = 0; i < _tree.children.length; i++){
                if(targetDepth === _currentDepth){
                    if(_tree.children[i]['name'] === name){
                        findVal = true;
                    }
                }else{
                    findVal = findRecur(_tree.children[i], _currentDepth + 1, _targetName);
                }
            }

            return findVal;

        }
        return findRecur(this.rxMap[0], 0, name)
    };

    RxTree.prototype.recursiveFunc = function(_arr, currentDt){
        var arr = _arr;
        var currentDepth = currentDt;

        for(var i = 0; i < arr.length; i++){
//             var isThere = this.findNodeFromOrigin(this.rxMap, arr[i]['name']);

            var isThere = this.hasNodeInSameDepth(i, arr[i]['name']);
            if(isThere){
                // next
            }else{
                // putting
                this.puttingDataToOrigin(this.rxMap[0]['children'], arr, arr[i]['name'], i+1, 0, this.rxMap[0]['id']);
            }
        }
    };
    RxTree.prototype.makeTree = function(_data){
        var data = _data;
        for(var i = 0; i < data.length; i++){
            var tempArr = data[i];
            var crrDt = 0;
            this.recursiveFunc(tempArr, crrDt);
        }
    };

    RxTree.prototype.getTree = function(_dataList, _option){
    	if(!_dataList) {return console.log("NO DATA LIST");}
        var eachDataList = this.createRightDataForm(_dataList, _option);
        this.makeTree(eachDataList);
        return this.rxMap;
    };

    RxTree.prototype.getMaxDepth = function(){
        return this.maxDepth + 2;
    };

    
    RxTreeUtilHTML.prototype.getRenderingDefaultOptions = function(){
        return {
            isOpenAll : "NONE",								// true, false, 'NONE' === not setting
            openDepth : 7,									// depth to open
            liOpenClassName : 'on',							// open li class name
            liCloseClassName : '',							// close li class name
            spanOpenClassName : 'icon-Remove',			// open span class name
            spanCloseClassName : 'icon-Add',			// close span class name
            callbackFunc : '',								// callbackFunc
        };
    };
    
    RxTreeUtilHTML.prototype.renderingHTML = function(_dom, _treeData, _options){
        var dom = _dom;
        var treeData = _treeData;
        var options = _options;

        function treeRecur(_refDom, _data, _depth){
            var refDom = _refDom;
            var data = _data;
            var depth = _depth;

            var liTag = document.createElement('LI');
            liTag.id = data['id'] + '_LI';

            if(options){
                var isOpenAll = options.isOpenAll ? options.isOpenAll : false;
                var openDepth = options.openDepth ? options.openDepth : 0;
                var liClassName = options.liOpenClassName ? options.liOpenClassName : '';
                var spanClassName = options.spanOpenClassName ? options.spanOpenClassName : '';
                if(openDepth <= depth){
                    spanClassName = options.spanCloseClassName ? options.spanCloseClassName : '';
                    liClassName = options.liCloseClassName ? options.liCloseClassName : '';
                }

                if(isOpenAll === true){
                    if(options.liOpenClassName){
                    	liTag.classList.add(options.liOpenClassName);
                    }
                }else{
                    if(isOpenAll === 'NONE'){
                        if(liClassName !== ''){
                            liTag.classList.add(liClassName);
                        }   
                    }
                }
                liTag.innerHTML = `<a href="#void"><span class="${spanClassName}"></span><input type="checkbox" id="${data['id']}"
                                data-parent-id="${data['parentId']}"
                                data-name="${data['name']}"
                                onclick='${options.callbackFunc}'
                                ><label for="${data['id']}">${data['name']}</label></a>`;
            }else{
                liTag.classList.add('on');
                liTag.innerHTML = `<a href="#void"><span class="icon-Add"></span><input type="checkbox" id="${data['id']}"
                                data-parent-id="${data['parentId']}"
                                data-name="${data['name']}"
                                onclick='${options.callbackFunc}'
                                ><label for="${data['id']}">${data['name']}</label></a>`;
            }


            if(depth === 0){
                var ulTag = document.createElement('UL');
                ulTag.setAttribute('id', data['id'] + '_UL');
                ulTag.classList.add('dep-rx' + (depth+1));
                ulTag.classList.add('check');
                ulTag.append(liTag);
                refDom.append(ulTag);
            }
            if(depth > 0){
                var parentLI = refDom;
                if(parentLI.childElementCount === 1){
                        var ulTag = document.createElement('UL');
                        ulTag.setAttribute('id', data['id'] + '_UL');
                        ulTag.classList.add('dep-rx' + (depth+1));
                        ulTag.classList.add('check');
                        ulTag.append(liTag);
                        refDom.append(ulTag);
                }else if(parentLI.childElementCount === 2){
                    parentLI.children[1].append(liTag);
                }
            }
            
            if(data['children']){
                if(data['children'].length > 0){
                    for(var i = 0; i < data['children'].length; i++){
                        treeRecur(liTag, data['children'][i], depth + 1);
                    }
                }
            }
        }

        treeRecur(dom, treeData[0], 0);
    };

    RxTreeUtilHTML.prototype.setOnlyChild = function(_dom){
        var dom = _dom;

        function treeRecur(_childDom, _depth){
            var childDom = _childDom;
            var depth = _depth;
            var targetUL = null;
            if(!childDom) return
            
            if(depth === 0){
                targetUL = childDom.children[0];
            }else{
            	if(childDom.childElementCount !== 1){
            		targetUL = childDom.children[1];
            	}
            }
            
            if(targetUL === null) return;
            for(var i = 0; i < targetUL.childElementCount; i++){
                var eachLI = targetUL.children[i];
                treeRecur(eachLI, depth + 1);
            }
            if(targetUL.childElementCount === 1){
            	targetUL.children[0].classList.add('rx-only-child');
            }
        }

        treeRecur(dom, 0);
    };
    
    window.RxTree = RxTree;
    window.RxTreeUtilHTML = RxTreeUtilHTML;
})(window);