import { createContext, useContext, useState, ReactNode } from "react";
import { IUser } from "../type"; 

interface UserContextType {
  user: IUser;
}

const UserContext = createContext<UserContextType | undefined>(undefined);

export const UserProvider = ({ children }: { children: ReactNode }) => {
  const [user] = useState<IUser>({
    id: 1, 
    username: "usuario",
  });

  return <UserContext.Provider value={{ user }}>{children}</UserContext.Provider>;
};

export const useUserContext = () => {
  const context = useContext(UserContext);
  if (!context) {
    throw new Error("useUserContext debe usarse dentro de un UserProvider");
  }
  return context;
};