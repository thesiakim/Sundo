function searchList() {
        const searchInput = document.getElementById('search-input');
        const searchTerm = searchInput.value.toLowerCase(); // 입력한 검색어를 소문자로 변환하여 저장
   
        const foldableList = document.getElementById('foldable-list');
        const listItems = foldableList.getElementsByTagName('li');
   
        // 검색어가 비어있을 경우 기존의 열린 UL, LI 요소 유지
        if (searchTerm === '') {
          // 모든 항목을 보이도록 설정
          for (let i = 0; i < listItems.length; i++) {
            const listItem = listItems[i];
            listItem.style.display = 'block';
          }
          return; // 검색어가 비어있으면 함수를 종료
        }
   
        // 모든 항목을 숨김
        for (let i = 0; i < listItems.length; i++) {
          const listItem = listItems[i];
          listItem.style.display = 'none';
        }
   
        // 검색어와 일치하는 항목을 열고, 상위 항목도 열어줌
        for (let i = 0; i < listItems.length; i++) {
          const listItem = listItems[i];
          const listItemText = listItem.innerText.toLowerCase();
   
          if (listItemText.includes(searchTerm)) {
            listItem.style.display = 'block';
   
            // 상위 항목을 열어줌
            let parentItem = listItem.parentNode;
            while (parentItem !== foldableList) {
              if (parentItem.tagName === 'UL') {
                parentItem.style.display = 'block';
              }
              parentItem = parentItem.parentNode;
            }
          }
        }
      }
   
      // 검색어 입력 시 이벤트 리스너 등록
      const searchInput = document.getElementById('search-input');
      searchInput.addEventListener('input', searchList);