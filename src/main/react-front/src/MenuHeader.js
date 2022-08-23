import { Button, ButtonGroup } from '@mui/material';
import { Box } from '@mui/system';

const headerBoxDefault = {
    height: 30,
    border: "1px solid black",
    padding: 0,
    maxWidth: 1000,
    minWidth: 298,
    m: 1
}

const headerButtonDefault = {
    height: 20,
    fontSize: "12px"
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