import { useNavigate } from "react-router-dom";
import { Button } from "@mui/material";

const RedirectionButtonArchived: React.FC = () => {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate("/archivedNotes"); 
  };

  return (
    <Button variant="contained" color="primary" onClick={handleClick} sx={{margin: "16px"}}>
      Ver Notas Archivadas
    </Button>
  );
};

export default RedirectionButtonArchived;