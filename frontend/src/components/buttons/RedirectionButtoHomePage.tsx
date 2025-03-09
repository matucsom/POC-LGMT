import { useNavigate } from "react-router-dom";
import { Button } from "@mui/material";

const RedirectionButtonHomePage: React.FC = () => {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate("/"); 
  };

  return (
    <Button variant="contained" color="secondary" onClick={handleClick}>
      Volver a Inicio
    </Button>
  );
};

export default RedirectionButtonHomePage;