import './App.css'
import { UserProvider } from './context/userContext';
import { NoteProvider } from './context/noteContext';
import RouterConfig from './router/RouterConfig';
import { ArchivedNoteProvider } from './context/archivedNoteContext';

const App: React.FC = () => {
  return (

    <div className="App">

      
      <UserProvider> 
      <NoteProvider>
      <ArchivedNoteProvider>
      <RouterConfig />  
      </ArchivedNoteProvider>
      </NoteProvider>
      </UserProvider>
      
      
    </div>
  );
};


export default App