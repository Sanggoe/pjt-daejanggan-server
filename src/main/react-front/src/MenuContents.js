// import { Stack, Button, ButtonGroup } from "@mui/material"
// import { Box } from "@mui/system"
// import React, { useState } from "react"

// const boxDefault = {
//     maxWidth: 1000,
//     minHeight: 400,
//     border: "1px solid black",
// }
// // border: "1px solid black",

// const stackDefault = {
//     minWidth: 300,
//     margin: 3,
//     border: "1px solid black",
// }
// // border: "1px solid black",

// const menuButtonDefault = {
//     borderRadius: 50,
//     minHeight: 65,
//     background: "#E6E6E6",
//     color: "#7F7F7F",
//     fontSize: "25px",
// }

// const menuButtonClicked = {
//     borderRadius: 50,
//     minHeight: 65,
//     background: "#FFE181",
//     color: "#7F7F7F",
//     fontSize: "25px",
// }


// export default function MenuContents(props) {
//     const [groupInfos, setGroupsInfo] = useState([
//         { id: 0, group: "LOA", toggle: false, style: menuButtonDefault },
//         { id: 1, group: "LOC", toggle: false, style: menuButtonDefault },
//         { id: 2, group: "60구절", toggle: false, style: menuButtonDefault  },
//         { id: 3, group: "DEP", toggle: false, style: menuButtonDefault  },
//         { id: 4, group: "180", toggle: false, style: menuButtonDefault  },
//         { id: 5, group: "OYO", toggle: false, style: menuButtonDefault  }
//     ])

//     const [] = useState([
//         ["전체 통계", "기간별 통계", "목록별 통계", "구절별 통계"]
//     ])

//     let subContents = <h2>안녕하세용</h2>;
    

//     function onChangeToggle(id) {
//         if (groupInfos[id].toggle) { // true 이면
//             groupInfos[id].toggle = false;
//             groupInfos[id].style = menuButtonDefault;
//             setGroupsInfo(groupInfos);
//             console.log(groupInfos[id].toggle + '로 변경')
//             subContents = <h2>눌렸습니다!! 노란버튼</h2>;
//         } else { // false 면
//             groupInfos[id].toggle = true
//             setGroupsInfo(groupInfos);
//             groupInfos[id].style = menuButtonClicked;
//             console.log(groupInfos[id].toggle + '로 변경')
//             subContents = <h2>다시 취소!! 회색버튼</h2>;
//         }
//     }

//     return (
//         <Box
//             display="flex"
//             justifyContent="center"
//             alignContent="center"
//             sx={boxDefault}>

//             <Stack
//                 spacing={2}
//                 sx={stackDefault}>
//                 {groupInfos.map((info) => (
//                     <>
//                         <Button
//                             id={info.id}
//                             style={info.style}
//                             onClick={(event) => {
//                                 event.preventDefault();
//                                 onChangeToggle(event.target.id);
//                             }}
//                             href="./">
//                             {info.group}
//                         </Button>
//                         {subContents}
//                     </>
//                 ))}

//                 {/* {contents} */}

//             </Stack>
//         </Box>
//     )
// }
