import { Button, ButtonGroup } from '@mui/material';
import { Box } from '@mui/system';

const headerBoxDefault = {
    maxWidth: 1000,
    height: 45,
    border: "1px solid black",
}
// border: "1px solid black",

const headerButtonDefault = {
    height: 40,
    fontSize: "15px",
    color: "black",
}

export default function MenuHeader() {
    return (
        <Box
            display="flex"
            justifyContent="flex-end"
            alignItems="center"
            sx={headerBoxDefault}>
            <ButtonGroup>
                <Button variant="text" sx={headerButtonDefault}>회원 관리</Button>
                <Button variant="text" sx={headerButtonDefault}>내 정보</Button>
                <Button variant="text" sx={headerButtonDefault}>로그아웃</Button>
            </ButtonGroup>
        </Box>
    );
}