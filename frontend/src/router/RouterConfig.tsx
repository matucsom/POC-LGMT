import React from "react";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import {HomePage} from "../pages/HomePage";
import {ArchivedNotesPage} from "../pages/ArchivedNotesPage";


const routes = [
  {
    path: "/",
    element: <HomePage/>,
  },{
    path: "/archivedNotes",
    element: <ArchivedNotesPage/>,
  }
  
];


const router = createBrowserRouter(routes);

const RouterConfig: React.FC = () => {
  return <RouterProvider router={router} />;
};

export default RouterConfig;